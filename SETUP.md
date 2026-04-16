# CampusMarks Console Setup

## What You Need

- Java 17 or later
- Maven 3.9 or later
- Node.js 20 or later
- npm 8 or later

## Runtime Choices

### Option 1. Quick Development Mode

This profile uses the embedded H2 database, so you can run the backend without preparing MySQL first.

### Option 2. MySQL-Backed Mode

This path keeps MySQL as the main database and uses the script in `backend/schema-and-data.sql`.

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

### Backend verification

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

Available backend endpoints:

- API: `http://localhost:8080/api`
- H2 Console: `http://localhost:8080/h2-console`
- Swagger UI: `http://localhost:8080/swagger-ui.html`

H2 console values:

- JDBC URL: `jdbc:h2:mem:studentdb`
- Username: `sa`
- Password: leave blank

### Backend using MySQL

1. Create the database and load the starter records:

```sql
SOURCE backend/schema-and-data.sql;
```

2. If needed, adjust `backend/src/main/resources/application.properties` for your local MySQL username or password.

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

## Optional Helper Script

Once Maven and npm dependencies are available, you can launch both apps with:

```powershell
Set-Location -LiteralPath "e:\Web Dev using Java\Project [College class]"
.\start-dev.ps1
```

## API Endpoints

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

## Troubleshooting

### `mvn` not found

Install Maven and confirm that it is available in `PATH`.

Suggested manual flow:

1. Download the current binary zip from the official Apache Maven site.
2. Extract it to a folder such as `C:\Tools\apache-maven`.
3. Add the Maven `bin` folder to your system `PATH`.
4. Open a new terminal and run:

```powershell
mvn -v
```

If that command succeeds, the backend commands in this guide should work as well.

### npm install fails

Check internet access, then run the install command again.

### frontend cannot reach backend

Confirm that the backend is active on port `8080`.

### MySQL mode fails

Check the following:

- MySQL server is running
- database exists
- username/password in `application.properties` are correct

## Notes

- The frontend is already configured to call `http://localhost:8080/api`.
- The backend allows CORS from `http://localhost:5173`.
- The `dev` profile is the simplest local startup path.
- The frontend dependency install and production build were already verified in this workspace.
- The default local administrator login is `admin / admin123`.
