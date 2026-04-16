# Local Setup Guide

## What You Need

- Java 17 or later
- Maven 3.9 or later
- Node.js 20 or later
- npm 8 or later

## Two Run Modes

### Option 1. Fast Local Dev

This uses the backend `dev` profile with embedded H2, so MySQL is not required.

### Option 2. Full MySQL Mode

This uses the default backend configuration and the MySQL script in `backend/schema-and-data.sql`.

## Install Commands

### Frontend dependencies

```powershell
Set-Location -LiteralPath "e:\Web Dev using Java\Project [College class]\frontend"
npm install
```

If you hit a local npm cache permission issue, use:

```powershell
Set-Location -LiteralPath "e:\Web Dev using Java\Project [College class]\frontend"
npm install --cache .npm-cache
```

### Backend build check

```powershell
Set-Location -LiteralPath "e:\Web Dev using Java\Project [College class]\backend"
mvn clean install
```

## Run Commands

### Backend with H2 dev profile

```powershell
Set-Location -LiteralPath "e:\Web Dev using Java\Project [College class]\backend"
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

Backend URLs:

- API: `http://localhost:8080/api`
- H2 Console: `http://localhost:8080/h2-console`
- Swagger UI: `http://localhost:8080/swagger-ui.html`

H2 login values:

- JDBC URL: `jdbc:h2:mem:studentdb`
- Username: `sa`
- Password: leave blank

### Backend with MySQL

1. Create the database and seed data:

```sql
SOURCE backend/schema-and-data.sql;
```

2. Update `backend/src/main/resources/application.properties` if your MySQL username or password differs.

3. Run the backend:

```powershell
Set-Location -LiteralPath "e:\Web Dev using Java\Project [College class]\backend"
mvn spring-boot:run
```

### Frontend

```powershell
Set-Location -LiteralPath "e:\Web Dev using Java\Project [College class]\frontend"
npm run dev
```

Frontend URL:

- `http://localhost:5173`

## One-Command Helper

After Maven and npm dependencies are installed, you can start both apps with:

```powershell
Set-Location -LiteralPath "e:\Web Dev using Java\Project [College class]"
.\start-dev.ps1
```

## Current API Groups

### Authentication

- `POST /api/auth/login`
- `GET /api/auth/me`

- `GET /api/students`
- `POST /api/students`
- `GET /api/students/{studentId}`
- `PUT /api/students/{studentId}`
- `DELETE /api/students/{studentId}`
- `GET /api/analytics/summary`
- `GET /api/analytics/topper`
- `GET /api/analytics/at-risk`
- `GET /api/analytics/department-averages`
- `GET /api/analytics/attendance-performance`

## If Something Fails

### `mvn` not found

Install Maven and make sure it is added to `PATH`.

Suggested manual flow:

1. Download the current binary zip from the official Apache Maven site.
2. Extract it to a folder such as `C:\Tools\apache-maven`.
3. Add the Maven `bin` folder to your system `PATH`.
4. Open a new terminal and run:

```powershell
mvn -v
```

If that works, the backend commands in this file will work too.

### npm install fails

Check internet access and rerun `npm install`.

### frontend cannot reach backend

Make sure backend is running on port `8080`.

### MySQL mode fails

Verify:

- MySQL server is running
- database exists
- username/password in `application.properties` are correct

## Notes

- The frontend is already configured to call `http://localhost:8080/api`.
- The backend allows CORS from `http://localhost:5173`.
- The `dev` profile is the easiest local startup path.
- The frontend dependency install and production build were verified successfully in this workspace.
- Default admin login for local development is `admin / admin123`.
