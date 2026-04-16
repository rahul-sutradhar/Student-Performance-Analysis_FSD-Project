import { NavLink, Route, Routes } from "react-router-dom";
import DashboardPage from "./pages/DashboardPage.jsx";
import StudentListPage from "./pages/StudentListPage.jsx";
import StudentFormPage from "./pages/StudentFormPage.jsx";
import AnalyticsPage from "./pages/AnalyticsPage.jsx";
import LoginPage from "./pages/LoginPage.jsx";
import ProtectedRoute from "./components/ProtectedRoute.jsx";
import { useAuth } from "./lib/AuthContext.jsx";

export default function App() {
  const { isAuthenticated, user, logout } = useAuth();

  const navItems = [
    { to: "/", label: "Dashboard" },
    { to: "/students", label: "Students" },
    { to: "/analytics", label: "Analytics" },
    ...(isAuthenticated ? [{ to: "/students/new", label: "Add Student" }] : []),
    { to: "/login", label: isAuthenticated ? "Admin Session" : "Login" }
  ];

  return (
    <div className="app-shell">
      <aside className="sidebar">
        <div>
          <p className="eyebrow">Academic Console</p>
          <h1>CampusMarks Console</h1>
          <p className="sidebar-copy">
            Review marks, attendance, and branch-level trends through one
            compact academic monitoring workspace.
          </p>

          <div className="auth-panel">
            <p className="auth-title">{isAuthenticated ? "Signed In" : "Read-Only Mode"}</p>
            <p className="auth-copy">
              {isAuthenticated
                ? `${user.username} can maintain records and update the dataset.`
                : "Sign in as administrator to change data entries."}
            </p>
            {isAuthenticated ? (
              <button type="button" className="secondary-button" onClick={logout}>
                Logout
              </button>
            ) : null}
          </div>
        </div>

        <nav className="nav-list">
          {navItems.map((item) => (
            <NavLink
              key={item.to}
              to={item.to}
              end={item.to === "/"}
              className={({ isActive }) =>
                isActive ? "nav-link nav-link-active" : "nav-link"
              }
            >
              {item.label}
            </NavLink>
          ))}
        </nav>
      </aside>

      <main className="page-shell">
        <Routes>
          <Route path="/" element={<DashboardPage />} />
          <Route path="/students" element={<StudentListPage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route
            path="/students/new"
            element={
              <ProtectedRoute>
                <StudentFormPage mode="create" />
              </ProtectedRoute>
            }
          />
          <Route
            path="/students/:studentId/edit"
            element={
              <ProtectedRoute>
                <StudentFormPage mode="edit" />
              </ProtectedRoute>
            }
          />
          <Route path="/analytics" element={<AnalyticsPage />} />
        </Routes>
      </main>
    </div>
  );
}
