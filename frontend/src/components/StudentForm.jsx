import { departments } from "../lib/constants.js";

export default function StudentForm({
  form,
  errors,
  submitting,
  onChange,
  onSubmit,
  submitLabel
}) {
  return (
    <form className="student-form" onSubmit={onSubmit}>
      <label>
        <span>Name</span>
        <input name="name" value={form.name} onChange={onChange} placeholder="Enter full name" />
        {errors.name ? <small className="field-error">{errors.name}</small> : null}
      </label>

      <label>
        <span>Department</span>
        <select name="department" value={form.department} onChange={onChange}>
          {departments.map((department) => (
            <option key={department} value={department}>
              {department}
            </option>
          ))}
        </select>
        {errors.department ? <small className="field-error">{errors.department}</small> : null}
      </label>

      <label>
        <span>Math Marks</span>
        <input name="math" type="number" min="0" max="100" value={form.math} onChange={onChange} />
        {errors.math ? <small className="field-error">{errors.math}</small> : null}
      </label>

      <label>
        <span>Science Marks</span>
        <input name="science" type="number" min="0" max="100" value={form.science} onChange={onChange} />
        {errors.science ? <small className="field-error">{errors.science}</small> : null}
      </label>

      <label>
        <span>Programming Marks</span>
        <input
          name="programming"
          type="number"
          min="0"
          max="100"
          value={form.programming}
          onChange={onChange}
        />
        {errors.programming ? <small className="field-error">{errors.programming}</small> : null}
      </label>

      <label>
        <span>Attendance</span>
        <input
          name="attendance"
          type="number"
          min="0"
          max="100"
          value={form.attendance}
          onChange={onChange}
        />
        {errors.attendance ? <small className="field-error">{errors.attendance}</small> : null}
      </label>

      <div className="form-actions">
        <button type="submit" className="primary-button" disabled={submitting}>
          {submitting ? "Saving..." : submitLabel}
        </button>
      </div>
    </form>
  );
}
