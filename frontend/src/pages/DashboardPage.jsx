import { useEffect, useState } from "react";
import MetricCard from "../components/MetricCard.jsx";
import PageHeader from "../components/PageHeader.jsx";
import { AttendanceScatterChart, DepartmentChart } from "../components/ChartsPanel.jsx";
import { analyticsApi } from "../lib/api.js";

export default function DashboardPage() {
  const [summary, setSummary] = useState(null);
  const [topper, setTopper] = useState(null);
  const [departmentAverages, setDepartmentAverages] = useState([]);
  const [attendanceData, setAttendanceData] = useState([]);
  const [error, setError] = useState("");

  useEffect(() => {
    async function loadDashboard() {
      try {
        const [summaryData, topperData, departmentData, attendancePerformance] = await Promise.all([
          analyticsApi.getSummary(),
          analyticsApi.getTopper(),
          analyticsApi.getDepartmentAverages(),
          analyticsApi.getAttendancePerformance()
        ]);

        setSummary(summaryData);
        setTopper(topperData);
        setDepartmentAverages(departmentData);
        setAttendanceData(attendancePerformance);
      } catch (loadError) {
        setError(loadError.message);
      }
    }

    loadDashboard();
  }, []);

  return (
    <section className="page-content">
      <PageHeader
        eyebrow="Overview"
        title="Academic summary board"
        description="A single-screen snapshot of class results, branch trends, and students who may need attention."
      />

      {error ? <div className="error-banner">{error}</div> : null}

      <div className="metrics-grid">
        <MetricCard label="Student Count" value={summary?.totalStudents ?? "--"} />
        <MetricCard label="Overall Average" value={summary ? `${summary.classAverage}` : "--"} />
        <MetricCard label="Support Watchlist" value={summary?.atRiskCount ?? "--"} />
        <MetricCard
          label="Highest Scorer"
          value={topper?.name ?? "--"}
          hint={topper ? `Mean score ${topper.averageMarks}` : "Waiting for score summary"}
        />
      </div>

      <div className="charts-grid">
        <DepartmentChart data={departmentAverages} />
        <AttendanceScatterChart data={attendanceData} />
      </div>
    </section>
  );
}
