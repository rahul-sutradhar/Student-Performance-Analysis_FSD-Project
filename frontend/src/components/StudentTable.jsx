import { Link } from "react-router-dom";

function calculateAverage(student) {
  if (!student.math || !student.science || !student.programming) return "-";
  const avg = (student.math + student.science + student.programming) / 3;
  return (Math.round(avg * 100) / 100).toFixed(2);
}

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
          {students.map((student) => {
            const average = calculateAverage(student);
            const isAtRisk = average !== "-" && parseFloat(average) < 60;
            return (
              <tr key={student.id}>
                <td>{student.name}</td>
                <td>{student.department}</td>
                <td>{student.math}</td>
                <td>{student.science}</td>
                <td>{student.programming}</td>
                <td>{student.attendance}%</td>
                <td>{average}</td>
                <td>
                  <span className={isAtRisk ? "pill pill-danger" : "pill pill-safe"}>
                    {isAtRisk ? "At Risk" : "Stable"}
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
            );
          })}
        </tbody>
      </table>
    </div>
  );
}
