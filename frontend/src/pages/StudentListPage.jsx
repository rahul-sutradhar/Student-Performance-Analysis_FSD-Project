import { useEffect, useMemo, useState } from "react";
import { Link } from "react-router-dom";
import PageHeader from "../components/PageHeader.jsx";
import StudentTable from "../components/StudentTable.jsx";
import { studentApi } from "../lib/api.js";
import { useAuth } from "../lib/AuthContext.jsx";
import { departments } from "../lib/constants.js";

export default function StudentListPage() {
  const { isAuthenticated } = useAuth();
  const [students, setStudents] = useState([]);
  const [selectedDepartment, setSelectedDepartment] = useState("");
  const [searchTerm, setSearchTerm] = useState("");
  const [error, setError] = useState("");

  useEffect(() => {
    async function loadStudents() {
      try {
        const data = await studentApi.getAll({
          department: selectedDepartment || undefined,
          search: searchTerm || undefined
        });
        setStudents(data);
      } catch (loadError) {
        setError(loadError.message);
      }
    }

    loadStudents();
  }, [selectedDepartment, searchTerm]);

  async function handleDelete(studentId) {
    const confirmed = window.confirm("Delete this student record?");
    if (!confirmed) {
      return;
    }

    try {
      await studentApi.remove(studentId);
      setStudents((currentStudents) => currentStudents.filter((student) => student.id !== studentId));
    } catch (deleteError) {
      if (deleteError.status === 401 || deleteError.status === 403) {
        setError("Administrator login is required before deleting an entry.");
        return;
      }
      setError(deleteError.message);
    }
  }

  return (
    <section className="page-content">
      <PageHeader
        eyebrow="Records"
        title="Academic register"
        description="Browse, filter, and maintain the score sheet used by the reporting screens."
        actions={
          isAuthenticated ? (
            <Link to="/students/new" className="primary-button">
              Add Entry
            </Link>
          ) : (
            <Link to="/login" className="primary-button">
              Login For Editing
            </Link>
          )
        }
      />

      <div className="toolbar">
        <input
          className="search-input"
          placeholder="Search by name"
          value={searchTerm}
          onChange={(event) => setSearchTerm(event.target.value)}
        />

        <select
          className="filter-select"
          value={selectedDepartment}
          onChange={(event) => setSelectedDepartment(event.target.value)}
        >
          <option value="">All Departments</option>
          {departments.map((department) => (
            <option key={department} value={department}>
              {department}
            </option>
          ))}
        </select>
      </div>

      {error ? <div className="error-banner">{error}</div> : null}

      <StudentTable students={students} onDelete={isAuthenticated ? handleDelete : undefined} />
    </section>
  );
}
