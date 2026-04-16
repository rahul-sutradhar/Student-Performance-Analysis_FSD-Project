import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import PageHeader from "../components/PageHeader.jsx";
import StudentForm from "../components/StudentForm.jsx";
import { studentApi } from "../lib/api.js";
import { useAuth } from "../lib/AuthContext.jsx";
import { initialStudentForm } from "../lib/constants.js";

export default function StudentFormPage({ mode }) {
  const navigate = useNavigate();
  const { studentId } = useParams();
  const { isAuthenticated } = useAuth();

  const [form, setForm] = useState(initialStudentForm);
  const [errors, setErrors] = useState({});
  const [submitting, setSubmitting] = useState(false);
  const [pageError, setPageError] = useState("");

  useEffect(() => {
    async function loadStudent() {
      if (mode !== "edit" || !studentId) {
        return;
      }

      try {
        const student = await studentApi.getById(studentId);
        setForm({
          name: student.name,
          department: student.department,
          math: String(student.math),
          science: String(student.science),
          programming: String(student.programming),
          attendance: String(student.attendance)
        });
      } catch (loadError) {
        setPageError(loadError.message);
      }
    }

    loadStudent();
  }, [mode, studentId]);

  function handleChange(event) {
    const { name, value } = event.target;
    setForm((currentForm) => ({ ...currentForm, [name]: value }));
    setErrors((currentErrors) => ({ ...currentErrors, [name]: "" }));
  }

  async function handleSubmit(event) {
    event.preventDefault();
    setSubmitting(true);
    setPageError("");

    const payload = {
      ...form,
      math: Number(form.math),
      science: Number(form.science),
      programming: Number(form.programming),
      attendance: Number(form.attendance)
    };

    try {
      if (mode === "edit") {
        await studentApi.update(studentId, payload);
      } else {
        await studentApi.create(payload);
      }
      navigate("/students");
    } catch (submitError) {
      if (submitError.status === 401 || submitError.status === 403) {
        setPageError("Your admin session is required for this action. Please login again.");
        return;
      }
      setErrors(submitError.validationErrors || {});
      setPageError(submitError.message);
    } finally {
      setSubmitting(false);
    }
  }

  return (
    <section className="page-content">
      <PageHeader
        eyebrow={mode === "edit" ? "Update" : "Create"}
        title={mode === "edit" ? "Edit academic entry" : "Add academic entry"}
        description="Enter marks and attendance carefully so the summary pages reflect the latest classroom data."
      />

      {!isAuthenticated ? (
        <div className="error-banner">Administrator access is required to modify academic entries.</div>
      ) : null}

      {pageError ? <div className="error-banner">{pageError}</div> : null}

      <StudentForm
        form={form}
        errors={errors}
        submitting={submitting}
        onChange={handleChange}
        onSubmit={handleSubmit}
        submitLabel={mode === "edit" ? "Update Entry" : "Create Entry"}
      />
    </section>
  );
}
