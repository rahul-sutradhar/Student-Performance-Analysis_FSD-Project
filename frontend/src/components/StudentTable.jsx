import { Link } from "react-router-dom";

export default function StudentTable({ students, onDelete }) {
  if (!students.length) {
    return <div className="empty-state">No students found for the current view.</div>;
  }

  return (
    <div className="table-wrap">
      <table>
        <thead>
          <tr>
            <th>Name</th>
            <th>Department</th>
            <th>Math</th>
            <th>Science</th>
            <th>Programming</th>
            <th>Attendance</th>
            <th>Average</th>
            <th>Status</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {students.map((student) => (
            <tr key={student.id}>
              <td>{student.name}</td>
              <td>{student.department}</td>
              <td>{student.math}</td>
              <td>{student.science}</td>
              <td>{student.programming}</td>
              <td>{student.attendance}%</td>
              <td>{student.averageMarks}</td>
              <td>
                <span className={student.atRisk ? "pill pill-danger" : "pill pill-safe"}>
                  {student.atRisk ? "At Risk" : "Stable"}
                </span>
              </td>
              <td className="action-cell">
                {onDelete ? (
                  <>
                    <Link to={`/students/${student.id}/edit`} className="text-action">
                      Edit
                    </Link>
                    <button
                      type="button"
                      className="text-action text-action-danger"
                      onClick={() => onDelete(student.id)}
                    >
                      Delete
                    </button>
                  </>
                ) : (
                  <span className="muted-copy">View Only</span>
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
