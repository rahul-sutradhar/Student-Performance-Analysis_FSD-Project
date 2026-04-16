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
          <p className="eyebrow">Capstone Project</p>
          <h1>Student Performance Analysis</h1>
          <p className="sidebar-copy">
            Track academic performance, identify at-risk students, and compare
            department outcomes from one admin dashboard.
          </p>

          <div className="auth-panel">
            <p className="auth-title">{isAuthenticated ? "Signed In" : "Read-Only Mode"}</p>
            <p className="auth-copy">
              {isAuthenticated
                ? `${user.username} can create, update, and delete records.`
                : "Login as admin to manage student data."}
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
