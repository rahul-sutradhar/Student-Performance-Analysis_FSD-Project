# Student Performance Analysis

Academic Project Report

Prepared from the implementation in this repository on April 20, 2026.

This report documents the design, implementation, deployment approach, and evaluation of a full-stack student performance monitoring system built with Spring Boot, React, MySQL/H2, JWT authentication, and chart-based analytics. The content is based on the actual codebase in this project together with current official documentation and education data references listed in the References section.

<<<PAGEBREAK>>>

## Table of Contents

1. Abstract
2. Introduction and Research Context
3. Problem Statement
4. Objectives
5. Scope of the Project
6. Feasibility and Requirements Analysis
7. Technology Research and Stack Justification
8. System Architecture
9. Functional Modules
10. Database Design
11. API Design
12. Frontend Design
13. Security, Validation, and Error Handling
14. Analytics Logic and Data Flow
15. Testing and Observed Results
16. Deployment Strategy
17. Limitations and Future Enhancements
18. Conclusion
19. References

<<<PAGEBREAK>>>

## Abstract

The Student Performance Analysis system is a full-stack academic dashboard created to store student records, monitor attendance, calculate academic trends, and present actionable insights through a web interface. The application is designed for an administrator-driven workflow where authenticated users can add, edit, and delete records, while read-oriented analytics remain openly accessible for demonstration and review. The backend is implemented using Spring Boot 3.3.5 with Spring Web, Spring Data JPA, Spring Security, and OpenAPI support. The frontend is implemented using React 18, Vite 5, React Router, and Recharts.

The project addresses a common academic administration problem: raw student marks and attendance values are often stored, but they are not transformed into usable insights such as branch averages, highest performer identification, watchlists for students below a threshold, or attendance-performance comparisons. This project converts that raw data into a decision-support dashboard.

The implementation supports two execution modes. In normal mode, it connects to the Spring Boot REST API and persists records using MySQL or H2. In demo mode, the React frontend automatically falls back to an in-browser mock data service if the backend is unavailable, which makes the project suitable for classroom demonstrations and GitHub Pages hosting. This dual-mode approach improves portability, presentation readiness, and resilience.

## Introduction and Research Context

Educational institutions increasingly rely on data systems not only to store records but also to support planning, intervention, and academic monitoring. UNESCO describes Education Management Information Systems as a central tool for decision-making and management across the education system, which reinforces the value of even small institutional dashboards that transform academic records into operational insight [R6]. UNESCO and World Bank material on educational data and learning outcomes also emphasizes that evidence-based monitoring is essential when institutions want to identify performance gaps early and intervene before students fall behind [R6], [R7].

Within this context, a student performance dashboard is a practical mini-EMIS style application. It does not attempt to replace a university ERP, but it demonstrates the core ideas behind educational data systems:

- collect structured student records
- validate academic and attendance inputs
- centralize access through a secure application layer
- compute summary metrics and trend views
- support intervention through watchlists and rankings

This project adopts that model and implements it as a modern web application using a Java backend and a React-based analytical frontend.

## Problem Statement

Many classroom or department-level academic processes still depend on spreadsheets, fragmented records, or manually calculated summaries. That workflow creates several issues:

- student records are difficult to search and update consistently
- branch-wise and class-wide analytics are not available instantly
- identifying top performers or at-risk students requires repeated manual calculation
- attendance and marks are stored separately in practice and rarely compared visually
- demonstration and deployment become difficult when the application depends entirely on a live backend

The project therefore solves the following problem:

How can we build a secure, maintainable, full-stack web application that stores student academic data and converts it into immediate visual insights for academic monitoring and review?

## Objectives

The primary objectives of the project are:

- to build a full-stack student performance management application
- to support authenticated administrator login using JWT-based sessions
- to provide CRUD operations for student records
- to compute overall average, branch averages, topper details, and support-watchlist counts
- to visualize attendance versus academic performance
- to offer both persistent backend mode and portable demo mode
- to prepare the application in a form suitable for academic submission and web demonstration

## Scope of the Project

The current project scope includes:

- a Spring Boot REST API for authentication, student management, and analytics
- a React single-page application with route-based navigation
- MySQL persistence for production-style usage
- H2 support for lightweight development usage
- OpenAPI and Swagger UI exposure for API inspection
- demo mode with mock data when the backend is not reachable
- GitHub Pages deployment support for the frontend

The current scope does not include:

- multi-user role hierarchies beyond a seeded admin account
- export to PDF/CSV from the application UI
- audit trails for record changes
- pagination for large-scale datasets
- advanced predictive analytics or machine learning models

## Feasibility and Requirements Analysis

### Operational Feasibility

The application is operationally feasible because it supports a familiar admin workflow: log in, manage student records, and inspect dashboards. The UI is designed around standard page flows such as login, dashboard, student list, student form, and analytics. The presence of mock mode also reduces operational friction for demonstrations.

### Technical Feasibility

The stack is technically feasible for a college-level full-stack project because:

- Spring Boot simplifies API, dependency injection, validation, and security configuration [R1], [R2]
- React provides component-based UI composition and predictable state handling [R3]
- Vite supplies fast development and static production builds for deployment [R4]
- MySQL offers familiar relational persistence with standard SQL schema creation [R5]

### Economic Feasibility

The project uses open-source technologies, common local development tools, and standard browser-based deployment for the frontend. This makes the solution low-cost to build and replicate in academic environments.

### Software Requirements

- Java 17
- Maven 3.9 or later
- Node.js 20 or later
- npm 8 or later
- MySQL 8.x for persistent mode
- Modern browser for frontend usage

### Hardware Requirements

- Standard development laptop or desktop
- Minimum 8 GB RAM recommended for running Java backend, Node frontend, and browser together
- Internet access required for remote deployment workflows, but not required for local demo mode

## Technology Research and Stack Justification

### Spring Boot Backend

Spring Boot is appropriate here because it integrates web APIs, dependency injection, validation, configuration management, data access, and security in one coherent platform [R1]. The project uses Spring Boot 3.3.5 in `backend/pom.xml`, with these relevant modules:

- `spring-boot-starter-web` for REST endpoints
- `spring-boot-starter-data-jpa` for persistence and repository abstractions
- `spring-boot-starter-security` for access control
- `spring-boot-starter-validation` for payload validation
- `springdoc-openapi-starter-webmvc-ui` for Swagger/OpenAPI documentation

Spring Security documentation notes that applications become secured when the security module is on the classpath, and this project extends that behavior with a stateless JWT filter and role-based restrictions [R2].

### React Frontend

React is well suited for dashboards because it structures the UI as reusable components and local or lifted state. The official React documentation emphasizes organized state management and component composition as applications grow [R3]. In this project, pages and components such as dashboard cards, tables, forms, and charts are split into separate modules under `frontend/src`.

### Vite Build System

Vite is used as the frontend build and development tool. Official Vite guidance states that `vite build` generates a production-ready bundle that can be served from static hosting, which directly supports the GitHub Pages deployment path used by this project [R4].

### MySQL and H2

MySQL is the primary persistent database target for production-style usage. The MySQL documentation for `CREATE TABLE` supports the relational schema choices used in the project, such as primary keys, auto-increment identifiers, and typed columns [R5]. H2 is also included as a lightweight runtime dependency for faster local testing and demonstration.

### JWT-Based Authentication

The project uses a stateless token-based model for admin sessions. This is appropriate for a REST API because the client can attach a bearer token to protected write requests, while the server remains stateless between calls.

### Research-Based Justification

From an educational systems perspective, this stack is justified because the application focuses on data collection, validation, summarization, and decision support. UNESCO's EMIS material stresses that education data systems are valuable when they improve management and evidence-based decision-making [R6]. The project directly reflects this idea by converting routine academic data into dashboard metrics and intervention-oriented views.

## System Architecture

The application follows a layered full-stack architecture.

### High-Level Flow

1. The user opens the React frontend.
2. The frontend calls REST endpoints exposed by the Spring Boot backend.
3. The backend routes requests through controller, service, repository, and entity layers.
4. JPA persists and retrieves data from MySQL or H2.
5. Analytics responses are computed from stored records and rendered as cards, tables, and charts.
6. If the backend is unavailable, the frontend switches to mock mode and serves equivalent responses locally.

### Architectural Layers

- Presentation layer: React pages and components in `frontend/src`
- Client data layer: fetch-based API wrapper and auth context in `frontend/src/lib`
- API layer: Spring MVC controllers under `backend/src/main/java/.../controller-equivalent packages`
- Business layer: `StudentCatalogService` and `InsightsService`
- Persistence layer: JPA repositories and entities
- Security layer: JWT issuance, JWT validation filter, admin bootstrap, and Spring Security configuration

### Practical Architecture Notes from the Codebase

- `StudentRecordController` exposes CRUD endpoints for students
- `InsightsController` exposes summary and analytics endpoints
- `SessionController` handles login and current-user lookup
- `ApiSecurityConfiguration` allows public reads but restricts write operations to authenticated admin users
- `frontend/src/lib/api.js` automatically falls back to mock mode when the backend times out or fails

## Functional Modules

### 1. Authentication Module

The authentication module exposes `POST /api/auth/login` and `GET /api/auth/me`. A seeded admin account is created at startup by `AdminBootstrap` using the username and password stored in application properties. Passwords are encoded using BCrypt. A signed token is issued after successful login and later validated by a custom authentication filter.

[[IMAGE:docs/images/login.png|Figure 1. Administrator login screen used for protected record management.|6.1]]

### 2. Student Management Module

This module supports:

- browse all students
- filter by department
- search by name fragment
- create a student record
- update an existing record
- delete a student record

The service logic lives in `StudentCatalogService`, while repository methods support combinations of branch and name filters.

### 3. Dashboard Module

The dashboard page presents:

- total student count
- overall class average
- support watchlist count
- highest scorer
- department averages chart
- attendance versus performance scatter chart

These values are fetched in parallel in `DashboardPage.jsx` using `Promise.all`, which keeps the screen responsive.

[[IMAGE:docs/images/dashboard.png|Figure 2. Dashboard view showing summary cards and analytics chart panels.|6.2]]

### 4. Analytics Module

The analytics page expands on the dashboard and includes:

- topper highlight
- support watchlist table
- department average chart
- attendance-performance scatter plot

This makes the system useful beyond simple CRUD by adding an academic decision-support layer.

### 5. Demo Mode Module

One of the strongest practical features of the implementation is demo mode. The frontend starts in backend mode on localhost, but if the API becomes unreachable or the application is deployed to a static host, it switches to a mock service from `mockData.js`. This preserves login, CRUD-like interactions, and analytics rendering for demonstration purposes.

## Database Design

The SQL bootstrap script defines two core tables:

1. `students`
2. `app_users`

### Students Table

The `students` table stores:

- `record_id`
- `name`
- `department`
- `math`
- `science`
- `programming`
- `attendance`

These map closely to the `StudentRecord` entity, where average score is derived at runtime rather than stored redundantly.

### Users Table

The `app_users` table stores:

- `account_id`
- `username`
- `password`
- `role`

This supports the single-admin authentication model used by the application.

### Seed Data Observations

The seed dataset contains 8 students across CS, IT, ENTC, MECHANICAL, and CIVIL branches. Based on the included data:

- overall class average is 75.88
- highest performer is Pooja Nair from IT with an average of 92.67
- the current support watchlist contains 2 students below the threshold of 60

Branch averages derived from the seed data are:

- CIVIL: 83.33
- CS: 75.33
- ENTC: 77.00
- IT: 80.83
- MECHANICAL: 59.00

## API Design

The API follows REST-style endpoint design.

### Authentication Endpoints

- `POST /api/auth/login`
- `GET /api/auth/me`

### Student Endpoints

- `GET /api/students`
- `GET /api/students/{studentId}`
- `POST /api/students`
- `PUT /api/students/{studentId}`
- `DELETE /api/students/{studentId}`

### Analytics Endpoints

- `GET /api/analytics/summary`
- `GET /api/analytics/topper`
- `GET /api/analytics/at-risk`
- `GET /api/analytics/department-averages`
- `GET /api/analytics/attendance-performance`

### API Design Strengths

- endpoint responsibilities are clearly separated
- GET endpoints remain public for read access and demonstration
- mutating operations are protected with `@PreAuthorize("hasRole('ADMIN')")`
- Swagger UI is enabled at `/swagger-ui.html`

## Frontend Design

The frontend is a React single-page application organized into pages, reusable components, and utility modules.

### Main Pages

- Login page
- Dashboard page
- Student list page
- Student form page
- Analytics page

[[IMAGE:docs/images/students.png|Figure 3. Student register page with search, department filter, and tabular academic records.|6.3]]

[[IMAGE:docs/images/analytics.png|Figure 4. Analytics page with topper summary, branch chart, attendance chart, and support watchlist.|6.3]]

### Reusable Components

- metric cards
- page header
- charts panel
- student table
- protected route wrapper
- student form

### State and Routing

React state is used for loading API responses, form values, and authentication context. React Router is used for page transitions, while the authentication provider holds token and current-user state centrally.

### UX Strengths

- clean separation of page-level and shared components
- parallel analytics loading on key screens
- automatic backend-failure fallback
- protected routes for admin-only write operations

## Security, Validation, and Error Handling

### Security Model

The application uses stateless security:

- login credentials are verified by the authentication manager
- passwords are stored as BCrypt hashes
- JWT tokens are signed and returned to the client
- a token filter processes bearer tokens on later requests
- write operations require admin role access

### Request Authorization Rules

From the security configuration:

- login, Swagger docs, Swagger UI, and H2 console are public
- GET requests to student and analytics endpoints are public
- other write operations require authentication

This is a practical compromise for a classroom project because it preserves easy demonstration while still protecting modification operations.

### Validation

The backend uses Jakarta Validation through the validation starter. Payload objects are annotated and validated before processing. This reduces invalid data entry at the API layer.

### Error Handling

The backend includes centralized support classes such as `GlobalExceptionHandler`, `ApiErrorResponse`, and `ResourceNotFoundException`. This allows the API to return structured error messages rather than raw stack traces.

## Analytics Logic and Data Flow

The `InsightsService` is the analytical core of the project.

### Business Rules

- class average is computed across student records
- highest scorer is pulled from a repository query ordered by mean score
- support watchlist uses a review threshold of 60.0
- department averages are aggregated by branch
- attendance-performance data is returned as points for scatter chart visualization

### Why This Matters

This logic transforms raw marks and attendance into interpretable indicators. That is the main academic value of the project. Instead of stopping at record storage, the system supports questions such as:

- Which branch is performing best?
- Which students require intervention?
- Does attendance appear to align with score outcomes?
- What is the current academic summary of the class?

### Implementation Strength

The repository layer uses focused JPA queries for ranking, watchlists, averages, and grouped branch statistics. This keeps analytics logic explicit and maintainable.

## Testing and Observed Results

### Local Validation Performed

On April 20, 2026:

- frontend production build completed successfully using `npm run build`
- backend Maven test command completed successfully using `mvn -q test`

### Frontend Build Observation

The frontend build produced a warning that the primary JavaScript bundle exceeded 500 kB after minification. This is not a build failure, but it indicates an optimization opportunity through code splitting or chunk configuration.

### Functional Results from Seed Data

- total students: 8
- overall average: 75.88
- highest scorer: Pooja Nair
- watchlist count: 2

These values match the intended analytics behavior in both the backend logic and the mock data model.

## Deployment Strategy

### Backend

The backend runs on port 8080 and can use:

- MySQL for persistent storage
- H2 through the dev profile for lightweight development

### Frontend

The frontend runs with Vite during development and builds into static assets for deployment.

### GitHub Pages Strategy

The repository already contains a GitHub Actions workflow for frontend deployment. This is appropriate because Vite's build output is static and suitable for static hosting [R4]. Since GitHub Pages cannot host the Spring Boot backend directly, the project uses one of two models:

1. deploy the backend separately and configure `VITE_API_BASE_URL`
2. deploy only the frontend and rely on demo mode for presentation

### Deployment Strength

This split deployment model is especially valuable for academic submission because the project can be demonstrated even when backend hosting is unavailable.

## Limitations and Future Enhancements

### Current Limitations

- single-admin authentication model
- no persistent audit history
- no CSV/PDF export from the application itself
- no pagination or lazy loading for large datasets
- analytics are descriptive rather than predictive
- public read endpoints may be too open for a production institutional deployment

### Recommended Future Enhancements

- add role-based access for faculty, HOD, and admin
- add import/export for CSV and Excel
- introduce historical semester-wise performance tracking
- add downloadable reports and printable analytics
- enable stricter production CORS and secret management
- add automated frontend tests and integration tests
- add code splitting to reduce the large production bundle warning
- extend analytics toward trend forecasting and risk scoring

## Conclusion

The Student Performance Analysis project successfully demonstrates a complete full-stack academic monitoring system. Its strongest quality is that it goes beyond CRUD and turns student data into usable academic insight. The backend uses a modern Spring Boot architecture with validation, persistence, security, and documented APIs. The frontend delivers a usable dashboard with routing, protected flows, charts, and a practical demo fallback mode. The database design is simple but appropriate, and the analytics pipeline is clear and traceable from stored records to UI charts.

As an academic project, the system is well aligned with the broader educational value of evidence-based data systems described in UNESCO and World Bank educational data resources [R6], [R7]. As a software project, it demonstrates sound full-stack engineering choices grounded in official framework guidance [R1], [R2], [R3], [R4], [R5]. With further work on roles, reporting, optimization, and advanced analytics, the project can evolve from a strong capstone-style submission into a more production-ready academic information platform.

## References

[R1] Spring Boot Documentation Overview and current maintained versions. Spring. Accessed April 20, 2026. https://docs.spring.io/spring-boot/documentation.html

[R2] Spring Security reference section in Spring Boot documentation. Spring. Accessed April 20, 2026. https://docs.spring.io/spring-boot/reference/web/spring-security.html

[R3] Managing State. React Documentation. Accessed April 20, 2026. https://react.dev/learn/managing-state

[R4] Building for Production. Vite Documentation. Accessed April 20, 2026. https://vite.dev/guide/build

[R5] MySQL 8.0 Reference Manual, CREATE TABLE Statement. Oracle. Accessed April 20, 2026. https://dev.mysql.com/doc/mysql/8.0/en/create-table.html

[R6] Education Management Information Systems - Progress Assessment Tool for Transformation. UNESCO. Updated February 23, 2026. Accessed April 20, 2026. https://www.unesco.org/en/education-management/emis-readiness-assessment

[R7] What is Learning Poverty. World Bank. Accessed April 20, 2026. https://www.worldbank.org/en/topic/education/brief/what-is-learning-poverty
