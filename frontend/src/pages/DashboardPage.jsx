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
        title="Academic performance dashboard"
        description="A quick view of student outcomes, class trends, and risk indicators."
      />

      {error ? <div className="error-banner">{error}</div> : null}

      <div className="metrics-grid">
        <MetricCard label="Total Students" value={summary?.totalStudents ?? "--"} />
        <MetricCard label="Class Average" value={summary ? `${summary.classAverage}` : "--"} />
        <MetricCard label="At-Risk Students" value={summary?.atRiskCount ?? "--"} />
        <MetricCard
          label="Top Performer"
          value={topper?.name ?? "--"}
          hint={topper ? `Average ${topper.averageMarks}` : "Waiting for analytics"}
        />
      </div>

      <div className="charts-grid">
        <DepartmentChart data={departmentAverages} />
        <AttendanceScatterChart data={attendanceData} />
      </div>
    </section>
  );
}
