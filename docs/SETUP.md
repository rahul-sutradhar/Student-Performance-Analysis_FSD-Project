# CampusMarks Console Setup

Setup guide for running the Student Performance Analysis project locally.

## Requirements

- Java 17 or later
- Maven 3.9 or later
- Node.js 20 or later
- npm 8 or later

## Runtime Modes

### Option 1. Quick Development Mode

Use the embedded H2 database for the fastest local setup. No MySQL preparation is required.

### Option 2. MySQL Mode

Use MySQL if you want the app to run with a persistent database loaded from the provided SQL script.

## Install Commands

### Frontend dependencies

```powershell
Set-Location -LiteralPath "E:\Web Dev using Java\Project [College class]\frontend"
npm install
```

If npm cache permissions cause issues:

```powershell
Set-Location -LiteralPath "E:\Web Dev using Java\Project [College class]\frontend"
npm install --cache .npm-cache
```

### Backend verification

```powershell
Set-Location -LiteralPath "E:\Web Dev using Java\Project [College class]\backend"
mvn clean install
```

## Run Commands

### Backend with H2 dev profile

```powershell
Set-Location -LiteralPath "E:\Web Dev using Java\Project [College class]\backend"
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

Available backend endpoints:

- API: `http://localhost:8080/api`
- H2 Console: `http://localhost:8080/h2-console`
- Swagger UI: `http://localhost:8080/swagger-ui.html`

H2 console values:

- JDBC URL: `jdbc:h2:mem:studentdb`
- Username: `sa`
- Password: leave blank

### Backend using MySQL

1. Create the database:

```sql
CREATE DATABASE student_performance_db;
```

2. Load the starter schema and records:

```sql
SOURCE sql/schema-and-data.sql;
```

3. Adjust credentials if needed in:

```text
backend/src/main/resources/application.properties
```

4. Start the backend:

```powershell
Set-Location -LiteralPath "E:\Web Dev using Java\Project [College class]\backend"
mvn spring-boot:run
```

### Frontend

```powershell
Set-Location -LiteralPath "E:\Web Dev using Java\Project [College class]\frontend"
npm run dev
```

Frontend URL:

- `http://localhost:5173`

### Frontend production build

```powershell
Set-Location -LiteralPath "E:\Web Dev using Java\Project [College class]\frontend"
$env:VITE_API_BASE_URL="https://your-backend-host/api"
npm run build
```

Production build output:

```text
frontend/dist
```

## Admin Login

Use the seeded administrator account:

```text
username: admin
password: admin123
```

## API Endpoints

### Authentication

- `POST /api/auth/login`
- `GET /api/auth/me`

### Student Records

- `GET /api/students`
- `GET /api/students/{studentId}`
- `POST /api/students`
- `PUT /api/students/{studentId}`
- `DELETE /api/students/{studentId}`

Optional query parameters for the list endpoint:

- `department`
- `search`

### Analytics

- `GET /api/analytics/summary`
- `GET /api/analytics/topper`
- `GET /api/analytics/at-risk`
- `GET /api/analytics/department-averages`
- `GET /api/analytics/attendance-performance`

## Troubleshooting

### `mvn` not found

Install Maven and confirm it is available in `PATH`.

Suggested manual flow:

1. Download the current binary zip from the official Apache Maven site.
2. Extract it to a folder such as `C:\Tools\apache-maven`.
3. Add the Maven `bin` folder to your system `PATH`.
4. Open a new terminal and run:

```powershell
mvn -v
```

### npm install fails

Check internet access and run the install command again.

### frontend cannot reach backend

Confirm the backend is running on port `8080`.

### GitHub Pages site loads but API requests fail

Check the following:

- `VITE_API_BASE_URL` points to your deployed backend
- the backend allows CORS from your GitHub Pages domain
- the backend is available over `https`

### MySQL mode fails

Check the following:

- MySQL server is running
- database exists
- credentials in `application.properties` are correct

## Notes

- The frontend is configured to call `http://localhost:8080/api`.
- For deployment, set `VITE_API_BASE_URL` to your public backend URL.
- The backend allows CORS from `http://localhost:5173`.
- The `dev` profile is the simplest local startup path.
- Create, update, and delete operations require admin authentication.
