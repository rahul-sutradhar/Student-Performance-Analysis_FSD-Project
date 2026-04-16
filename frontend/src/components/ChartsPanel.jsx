import {
  Bar,
  BarChart,
  CartesianGrid,
  Cell,
  Legend,
  ResponsiveContainer,
  Scatter,
  ScatterChart,
  Tooltip,
  XAxis,
  YAxis
} from "recharts";

const departmentColors = ["#134074", "#ef8354", "#0b6e4f", "#8f2d56", "#6c757d"];

export function DepartmentChart({ data }) {
  return (
    <div className="chart-card">
      <h3>Branch Comparison</h3>
      <ResponsiveContainer width="100%" height={280}>
        <BarChart data={data}>
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey="department" />
          <YAxis domain={[0, 100]} />
          <Tooltip />
          <Legend />
          <Bar dataKey="averageMarks" fill="#134074" radius={[8, 8, 0, 0]}>
            {data.map((entry, index) => (
              <Cell key={entry.department} fill={departmentColors[index % departmentColors.length]} />
            ))}
          </Bar>
        </BarChart>
      </ResponsiveContainer>
    </div>
  );
}

export function AttendanceScatterChart({ data }) {
  return (
    <div className="chart-card">
      <h3>Attendance and Mean Score</h3>
      <ResponsiveContainer width="100%" height={280}>
        <ScatterChart>
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis type="number" dataKey="attendance" name="Attendance" unit="%" domain={[0, 100]} />
          <YAxis type="number" dataKey="averageMarks" name="Average Marks" domain={[0, 100]} />
          <Tooltip cursor={{ strokeDasharray: "3 3" }} />
          <Scatter data={data} fill="#ef8354" />
        </ScatterChart>
      </ResponsiveContainer>
    </div>
  );
}
