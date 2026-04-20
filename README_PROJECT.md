# Student Performance Analysis

A full-stack Java FSD project for tracking student academic records, managing attendance and marks, and visualizing performance insights through a Spring Boot + React dashboard.

Built as a college full-stack project with a Java backend, React frontend, SQL bootstrap script, JWT-based admin access, and a demo-ready fallback mode for static deployment.

---

## What this project is

This project is an academic performance monitoring system designed for department-level or classroom-level use. It stores student records, supports administrator-controlled CRUD operations, and converts raw marks plus attendance data into useful dashboard insights such as:

1. overall class average
2. highest scorer
3. support watchlist
4. department-wise average comparison
5. attendance versus academic performance analysis

The application has two practical usage modes:

1. **Backend-connected mode** using Spring Boot with MySQL or H2
2. **Demo mode** using frontend mock data when the backend is unavailable

That makes the project suitable both for local development and for academic presentation on GitHub Pages.

---

## Repository layout

```text
.
|-- backend/                              # Spring Boot REST API
|   |-- pom.xml                           # Maven configuration and dependencies
|   `-- src/
|       |-- main/java/com/studentperformance/backend/
|       |   |-- analytics/                # Analytics endpoints and business logic
|       |   |-- security/                 # JWT auth, admin bootstrap, security config
|       |   |-- student/                  # Entity, repository, service, controller
|       |   `-- support/                  # Error handling and shared support classes
|       `-- main/resources/
|           |-- application.properties    # MySQL-oriented configuration
|           |-- application-dev.properties# H2 development profile
|           `-- data.sql                  # Development seed data
|-- frontend/                             # React + Vite dashboard
|   |-- package.json                      # Frontend scripts and dependencies
|   |-- vite.config.js                    # Vite configuration
|   `-- src/
|       |-- components/                   # Shared UI components
|       |-- lib/                          # API layer, auth context, constants, mock data
|       `-- pages/                        # Dashboard, analytics, login, students, form
|-- sql/
|   `-- schema-and-data.sql               # MySQL schema and starter data
|-- docs/
|   |-- SETUP.md                          # Project setup notes
|   |-- Project_Workflow_Plan.md          # Workflow and project planning notes
|   |-- Student_Performance_Project_Report.md
|   |-- Student_Performance_Project_Report.docx
|   `-- images/                           # Real UI screenshots used in documentation
|-- .github/workflows/
|   `-- deploy-pages.yml                  # GitHub Pages deployment workflow
|-- python/
|   `-- generate_report_docx.py           # Helper to generate the report DOCX
|-- .gitignore
|-- README.md                             # Original README
`-- README_PROJECT.md                     # This file
```

---

## Prerequisites

- **Java 17**
- **Maven 3.9+**
- **Node.js 20+**
- **npm 8+**
- **MySQL 8.x** for persistent database mode
- A modern browser for using the frontend

Optional:

- **H2** is already included through the Spring Boot dependencies for development mode

---

## Setup (one time)

All commands below assume you are in the project root.

### 1. Create the MySQL database

```sql
CREATE DATABASE student_performance_db;
```

### 2. Load the starter schema and sample data

```sql
SOURCE sql/schema-and-data.sql;
```

### 3. Configure backend credentials

Update the datasource settings in:

```text
backend/src/main/resources/application.properties
```

Important properties already defined there include:

- `spring.datasource.url`
- `spring.datasource.username`
- `spring.datasource.password`
- `security.jwt.secret`
- `app.admin.username`
- `app.admin.password`

### 4. Install frontend dependencies

```bash
cd frontend
npm install
```

---

## Running the project

All commands assume you are in the project root unless stated otherwise.

### 1. Run the backend in development mode with H2

```bash
cd backend
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

This is the fastest way to start the backend locally without setting up MySQL persistence.

Backend URLs:

- API base: `http://localhost:8080/api`
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- H2 console: `http://localhost:8080/h2-console`

### 2. Run the backend with MySQL

```bash
cd backend
mvn spring-boot:run
```

### 3. Run the frontend

Open a second terminal:

```bash
cd frontend
npm run dev
```

Frontend URL:

- `http://localhost:5173`

### 4. Default admin login

The seeded administrator credentials are:

```text
username: admin
password: admin123
```

These can be changed in `backend/src/main/resources/application.properties`.

---

## Demo mode

The frontend includes a built-in mock mode for situations where the backend is unavailable. This is especially useful for:

- classroom demonstrations
- GitHub Pages deployment
- UI testing without starting the backend

How it works:

1. The frontend tries to call the backend API.
2. If the request times out or fails, it switches to mock mode automatically.
3. The app continues working with in-memory sample data from `frontend/src/lib/mockData.js`.

What still works in demo mode:

- login screen
- dashboard
- student listing
- search and department filter
- add, edit, and delete actions
- analytics views

Important limitation:

- mock changes are stored only in browser memory for that session and reset on refresh

To test demo mode locally:

```bash
cd frontend
npm run dev
```

Then simply leave the backend stopped. After the frontend cannot reach the API, it will fall back to mock mode.

---

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

---

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

---

## GitHub Pages deployment

GitHub Pages can host only the React frontend. The Spring Boot backend must be deployed separately if you want real persistent data in production.

This project already includes:

```text
.github/workflows/deploy-pages.yml
frontend/.env.example
```

If you deploy only the frontend, the app can still run in demo mode with mock data.

If you deploy the backend elsewhere, define the production API URL using:

```text
VITE_API_BASE_URL=https://your-backend-host/api
```

Deployment flow:

1. Optionally deploy the backend first
2. Add `VITE_API_BASE_URL` as a repository secret if using a real backend
3. Set GitHub Pages source to `GitHub Actions`
4. Push to `main`
5. Let the workflow publish the frontend build

---

## Troubleshooting

- **Frontend starts but charts or data do not load.** Confirm the backend is running on `http://localhost:8080`, or intentionally use demo mode by leaving the backend off.
- **MySQL connection fails.** Recheck `spring.datasource.url`, username, password, and whether the `student_performance_db` database exists.
- **Admin login does not work.** Verify `app.admin.username` and `app.admin.password` in `application.properties`, and ensure the backend has restarted after changes.
- **Write actions fail with unauthorized or forbidden errors.** Login again and make sure the token is stored in the browser session.
- **Frontend route refresh fails on static hosting.** This project already uses hash-based routing, which is the correct approach for GitHub Pages.
- **Frontend works but data resets on refresh.** That means you are in demo/mock mode, not backend-connected mode.
- **Swagger UI does not open.** Confirm the backend is running and check `springdoc.swagger-ui.path=/swagger-ui.html`.

---

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

---

## Project highlights

- Full-stack Java + React implementation
- JWT-based admin authentication
- CRUD operations with validation
- Analytics-oriented dashboard, not just record storage
- Department average and attendance-performance insights
- Demo mode for easy showcase without backend hosting
- Swagger documentation for API review
- GitHub Pages workflow for frontend deployment

---

## Documentation mapping

- Setup guide: `docs/SETUP.md`
- Workflow notes: `docs/Project_Workflow_Plan.md`
- Project report source: `docs/Student_Performance_Project_Report.md`
- Project report document: `docs/Student_Performance_Project_Report.docx`
- SQL bootstrap script: `sql/schema-and-data.sql`

---

## Validation status

The project has already been validated with:

- `mvn -q test`
- `npm run build`

The frontend build completes successfully, with one non-blocking bundle-size warning from Vite about a large generated JavaScript chunk.

---

## Submission checklist

- [x] Spring Boot backend starts successfully
- [x] React frontend runs locally
- [x] Admin login works with JWT-based flow
- [x] Student CRUD pages are implemented
- [x] Analytics dashboard is implemented
- [x] SQL schema and seed script are included
- [x] Swagger/OpenAPI UI is available
- [x] GitHub Pages workflow is included
- [x] Project report is included in `docs/`
- [x] Original README remains untouched
- [x] New project README is added separately
