import { useEffect, useState } from "react";
import PageHeader from "../components/PageHeader.jsx";
import { AttendanceScatterChart, DepartmentChart } from "../components/ChartsPanel.jsx";
import StudentTable from "../components/StudentTable.jsx";
import { analyticsApi } from "../lib/api.js";

export default function AnalyticsPage() {
  const [topper, setTopper] = useState(null);
  const [atRiskStudents, setAtRiskStudents] = useState([]);
  const [departmentAverages, setDepartmentAverages] = useState([]);
  const [attendanceData, setAttendanceData] = useState([]);
  const [error, setError] = useState("");

  useEffect(() => {
    async function loadAnalytics() {
      try {
        const [topperData, atRiskData, departmentData, attendancePerformance] = await Promise.all([
          analyticsApi.getTopper(),
          analyticsApi.getAtRisk(),
          analyticsApi.getDepartmentAverages(),
          analyticsApi.getAttendancePerformance()
        ]);

        setTopper(topperData);
        setAtRiskStudents(atRiskData);
        setDepartmentAverages(departmentData);
        setAttendanceData(attendancePerformance);
      } catch (loadError) {
        setError(loadError.message);
      }
    }

    loadAnalytics();
  }, []);

  return (
    <section className="page-content">
      <PageHeader
        eyebrow="Insights"
        title="Performance analytics"
        description="Review toppers, department averages, risk groups, and attendance correlation from one page."
      />

      {error ? <div className="error-banner">{error}</div> : null}

      <div className="spotlight-card">
        <p className="eyebrow">Top Performer</p>
        <h3>{topper?.name ?? "--"}</h3>
        <p className="page-description">
          {topper
            ? `${topper.department} department with an average score of ${topper.averageMarks}.`
            : "Top performer details will appear here once data loads."}
        </p>
      </div>

      <div className="charts-grid">
        <DepartmentChart data={departmentAverages} />
        <AttendanceScatterChart data={attendanceData} />
      </div>

      <section className="subsection">
        <h3>Students Requiring Support</h3>
        <p className="page-description">
          Students with average marks below the project threshold are listed here.
        </p>
        <StudentTable students={atRiskStudents} />
      </section>
    </section>
  );
}
