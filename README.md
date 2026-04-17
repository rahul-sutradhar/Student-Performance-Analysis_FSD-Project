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
- Swagger/OpenAPI documentation
- SQL setup script included for MySQL mode

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

Before deployment, set the production API URL:

```text
VITE_API_BASE_URL=https://your-backend-host/api
```

This repository now includes:

```text
.github/workflows/deploy-pages.yml
frontend/.env.example
```

Deployment flow:

1. Deploy the backend first and confirm the public API URL works.
2. Add a GitHub repository secret named `VITE_API_BASE_URL` with your backend URL.
3. In repository settings, set GitHub Pages source to `GitHub Actions`.
4. Push to `main` to trigger the frontend deployment workflow.

The frontend uses hash-based routing so page refreshes work correctly on GitHub Pages.

## API Endpoints

```text
POST /api/auth/login
GET  /api/auth/me
GET  /api/students
GET  /api/students/{studentId}
POST /api/students
PUT  /api/students/{studentId}
DELETE /api/students/{studentId}
GET  /api/analytics/summary
GET  /api/analytics/topper
GET  /api/analytics/at-risk
GET  /api/analytics/department-averages
GET  /api/analytics/attendance-performance
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

## Notes

The application supports read-only browsing for general users and protected write operations for authenticated administrators. The `dev` profile uses an in-memory H2 database for quick local startup, while the standard profile is intended for MySQL-backed execution. For deployment, the frontend reads its API base URL from `VITE_API_BASE_URL` and falls back to `http://localhost:8080/api` during local development.
