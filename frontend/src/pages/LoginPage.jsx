import { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import PageHeader from "../components/PageHeader.jsx";
import { useAuth } from "../lib/AuthContext.jsx";

export default function LoginPage() {
  const navigate = useNavigate();
  const location = useLocation();
  const { login } = useAuth();

  const [form, setForm] = useState({
    username: "admin",
    password: "admin123"
  });
  const [error, setError] = useState("");
  const [submitting, setSubmitting] = useState(false);

  function handleChange(event) {
    const { name, value } = event.target;
    setForm((currentForm) => ({ ...currentForm, [name]: value }));
  }

  async function handleSubmit(event) {
    event.preventDefault();
    setSubmitting(true);
    setError("");

    try {
      await login(form);
      const redirectPath = location.state?.from || "/students";
      navigate(redirectPath, { replace: true });
    } catch (loginError) {
      setError(loginError.message);
    } finally {
      setSubmitting(false);
    }
  }

  return (
    <section className="page-content">
      <PageHeader
        eyebrow="Administrator Access"
        title="Login to edit records"
        description="The summary pages are viewable without a session, but data changes require a signed administrator token."
      />

      {error ? <div className="error-banner">{error}</div> : null}

      <form className="student-form login-form" onSubmit={handleSubmit}>
        <label>
          <span>Username</span>
          <input name="username" value={form.username} onChange={handleChange} />
        </label>

        <label>
          <span>Password</span>
          <input name="password" type="password" value={form.password} onChange={handleChange} />
        </label>

        <div className="login-note">
          Local development credentials: <strong>admin / admin123</strong>
        </div>

        <div className="form-actions">
          <button type="submit" className="primary-button" disabled={submitting}>
            {submitting ? "Signing In..." : "Login"}
          </button>
        </div>
      </form>
    </section>
  );
}
