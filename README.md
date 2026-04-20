# Student Performance Analysis

Full-stack Java FSD project for tracking student academic records, managing attendance and marks, and visualizing performance insights through a Spring Boot + React dashboard.

## Features

- JWT-based admin login with seeded administrator account
- REST APIs for student management and analytics
- Spring Boot backend with JPA/Hibernate
- React + Vite frontend with protected admin routes
- Dashboard summary cards for student count, class average, top performer, and support watchlist
- Student list with branch and search filters
- Add, edit, and delete student records
- Analytics views for topper, at-risk students, department averages, and attendance vs performance
- Swagger documentation
- SQL setup script included for MySQL mode
- **Demo mode with mock data** - Full functionality without backend, perfect for GitHub Pages deployment and testing

## Tech stack summary

| Layer | Technology |
|---|---|
| Backend framework | Spring Boot 3.3.5 |
| API style | REST with Spring MVC |
| Persistence | Spring Data JPA / Hibernate |
| Database | MySQL, H2 |
| Security | Spring Security + JWT |
| API docs | springdoc OpenAPI / Swagger UI |
| Frontend | React 18 |
| Build tool | Vite 5 |
| Routing | React Router |
| Charts | Recharts |
| Package managers | Maven, npm |
| Language versions | Java 17, modern JavaScript |


## Project Structure

```text
Project [College class]/
  .github/workflows/           GitHub Pages deployment workflow
  backend/                    Spring Boot REST API
  frontend/                   React + Vite dashboard
  docs/                       setup guide and workflow notes
  sql/schema-and-data.sql     MySQL schema and seed script
  data/                       reserved for datasets or exports
  python/                     reserved for future Python analysis
```

## Backend Setup

Prerequisites:

- Java 17
- Maven 3.9+
- MySQL for database mode, or use the included H2 dev profile

Create the MySQL database if you want persistent storage:

```sql
CREATE DATABASE student_performance_db;
```

Load the starter schema and data:

```sql
SOURCE sql/schema-and-data.sql;
```

Update database credentials in:

```text
backend/src/main/resources/application.properties
```

Run the backend with the H2 development profile:

```bash
cd backend
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

Run the backend with MySQL:

```bash
cd backend
mvn spring-boot:run
```

The backend starts on:

```text
http://localhost:8080
```

Swagger is available at:

```text
http://localhost:8080/swagger-ui.html
```

Seeded login:

```text
username: admin
password: admin123
```

## Frontend Setup

Prerequisites:

- Node.js 20+
- npm 8+

Run the frontend:

```bash
cd frontend
npm install
npm run dev
```

Open:

```text
http://localhost:5173
```

## GitHub Pages Deployment

GitHub Pages can host the React frontend only. The Spring Boot backend must be deployed separately on a platform that supports Java applications.

**Important:** The frontend will work on GitHub Pages using demo mode with mock data, even without deploying the backend. This means the full app (student management, add/edit/delete, analytics) is fully functional on GitHub Pages.

Before deployment, set the production API URL (optional, only if deploying backend):

```text
VITE_API_BASE_URL=https://your-backend-host/api
```

This repository now includes:

```text
.github/workflows/deploy-pages.yml
frontend/.env.example
```

Deployment flow:

1. *(Optional)* Deploy the backend first if you want to use real data instead of mock data.
2. *(Optional)* Add a GitHub repository secret named `VITE_API_BASE_URL` with your backend URL.
3. In repository settings, set GitHub Pages source to `GitHub Actions`.
4. Push to `main` to trigger the frontend deployment workflow.

The frontend uses hash-based routing so page refreshes work correctly on GitHub Pages, and demo mode ensures full functionality without the backend.

## Demo Mode (Mock Data)

The frontend has a built-in demo mode that works completely offline using mock data. This is useful for testing the UI without running the backend, and is automatically used when deployed to GitHub Pages.

**How it works:**

- When the backend is unreachable (after a 5-second timeout), the app automatically switches to demo mode
- All student records, add, edit, delete, and analytics operations work with mock data
- Changes are stored in the browser's memory during the session (data resets on page refresh)
- No authentication required for admin operations in demo mode

**Default login for demo mode:**

```text
username: admin
password: admin123
```

**What you can do in demo mode:**

- Browse 8 sample students
- Filter by department
- Search by student name
- Add new student records
- Edit existing records
- Delete records
- View analytics dashboard with mock insights

To test demo mode locally, you can start just the frontend without the backend:

```bash
cd frontend
npm run dev
```

Wait 5 seconds, and the app will switch to demo mode automatically. On GitHub Pages, demo mode is used by default.

## API overview

### Authentication

```text
POST /api/auth/login
GET  /api/auth/me
```

### Student management

```text
GET    /api/students
GET    /api/students/{studentId}
POST   /api/students
PUT    /api/students/{studentId}
DELETE /api/students/{studentId}
```

### Analytics

```text
GET /api/analytics/summary
GET /api/analytics/topper
GET /api/analytics/at-risk
GET /api/analytics/department-averages
GET /api/analytics/attendance-performance
```

## Documentation Mapping

Project setup steps are documented in:

```text
docs/SETUP.md
```

Project scope and workflow notes are documented in:

```text
docs/Project_Workflow_Plan.md
```

Database bootstrap script:

```text
sql/schema-and-data.sql
```

## Quick evaluation recipe

To verify the project in a few minutes:

1. Start the backend with `mvn spring-boot:run -Dspring-boot.run.profiles=dev`
2. Start the frontend with `npm run dev`
3. Open `http://localhost:5173`
4. Check the dashboard summary cards for student count, average, watchlist, and topper
5. Open the Students page and verify search plus department filtering
6. Open the Login page and sign in with `admin / admin123`
7. Add a new student record and confirm it appears in the table
8. Edit or delete a record and verify the change is reflected
9. Open the Analytics page and review topper, branch comparison, attendance chart, and at-risk students
10. Open Swagger UI at `http://localhost:8080/swagger-ui.html` and verify the documented endpoints

## Current Functional Scope

### Implemented

- admin authentication with JWT
- student CRUD operations
- dashboard summary analytics
- topper endpoint
- at-risk student list
- department average analysis
- attendance vs performance analysis
- frontend dashboard, student list, login, analytics, and student form pages

### Future Extensions

- CSV import/export
- downloadable reports
- pagination
- audit history
- role-based access beyond a single admin account

## Troubleshooting

- **Frontend starts but charts or data do not load.** Confirm the backend is running on `http://localhost:8080`, or intentionally use demo mode by leaving the backend off.
- **MySQL connection fails.** Recheck `spring.datasource.url`, username, password, and whether the `student_performance_db` database exists.
- **Admin login does not work.** Verify `app.admin.username` and `app.admin.password` in `application.properties`, and ensure the backend has restarted after changes.
- **Write actions fail with unauthorized or forbidden errors.** Login again and make sure the token is stored in the browser session.
- **Frontend route refresh fails on static hosting.** This project already uses hash-based routing, which is the correct approach for GitHub Pages.
- **Frontend works but data resets on refresh.** That means you are in demo/mock mode, not backend-connected mode.
- **Swagger UI does not open.** Confirm the backend is running and check `springdoc.swagger-ui.path=/swagger-ui.html`.

## Notes

The application supports read-only browsing for general users and protected write operations for authenticated administrators. The `dev` profile uses an in-memory H2 database for quick local startup, while the standard profile is intended for MySQL-backed execution. For deployment, the frontend reads its API base URL from `VITE_API_BASE_URL` and falls back to `http://localhost:8080/api` during local development.

Demo mode provides a fully functional frontend for testing and showcase purposes without requiring backend deployment. The mock data includes 8 sample students across different departments with realistic performance metrics. Frontend changes made in demo mode are temporary and stored in browser memory only.
