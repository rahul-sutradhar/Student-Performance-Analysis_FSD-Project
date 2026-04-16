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
        title="Score patterns"
        description="Compare branch performance, inspect attendance impact, and review the current support watchlist."
      />

      {error ? <div className="error-banner">{error}</div> : null}

      <div className="spotlight-card">
        <p className="eyebrow">Highest Scorer</p>
        <h3>{topper?.name ?? "--"}</h3>
        <p className="page-description">
          {topper
            ? `${topper.department} branch with a mean score of ${topper.averageMarks}.`
            : "Lead scorer details will appear here after the data is loaded."}
        </p>
      </div>

      <div className="charts-grid">
        <DepartmentChart data={departmentAverages} />
        <AttendanceScatterChart data={attendanceData} />
      </div>

      <section className="subsection">
        <h3>Support Watchlist</h3>
        <p className="page-description">
          This list highlights students whose current mean score falls below the review threshold.
        </p>
        <StudentTable students={atRiskStudents} />
      </section>
    </section>
  );
}
