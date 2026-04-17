# Student Performance Analysis - Workflow Plan

Workflow reference for building, presenting, and extending the Student Performance Analysis project.

## Project Summary

The project is designed as a full-stack academic monitoring system built with Spring Boot, React, and MySQL or H2. Its purpose is to help an administrator maintain student records and review meaningful performance trends from one dashboard.

## Final Project Interpretation

Build a student performance dashboard that supports:

- student record management
- branch-wise academic analysis
- top performer identification
- at-risk student tracking
- attendance vs marks analysis

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

## Core Data Model

Primary student fields used in the application:

- `studentId`
- `name`
- `department`
- `math`
- `science`
- `programming`
- `attendance`

Derived or calculated values:

- `averageMarks`
- `riskStatus`

## Functional Modules

### 1. Authentication

- admin login
- token-based session handling
- protected write operations

### 2. Student Management

- add new students
- edit existing student records
- delete student records
- list students with filtering

### 3. Performance Analytics

- calculate average performance
- identify class topper
- identify at-risk students
- compute department-wise averages

### 4. Attendance Analysis

- compare attendance with marks
- generate attendance-performance chart data

### 5. Dashboard and UI

- overview cards
- analytics page
- branch and search filtering
- protected student form flow

## Suggested Development Workflow

### Phase 1. Requirement Freeze

Confirm:

- project title
- final feature list
- admin-only edit policy
- analytics outputs to be shown

### Phase 2. Data and Schema Planning

Prepare:

- student table structure
- allowed branch values
- marks validation rules
- attendance limits
- seed data

### Phase 3. Backend Development

Build and validate:

- entity layer
- repository layer
- service layer
- controller layer
- authentication flow
- analytics endpoints

### Phase 4. Frontend Development

Build:

- route structure
- login flow
- dashboard screen
- student listing screen
- student create/edit form
- analytics page and visualizations

### Phase 5. Integration

Connect in this order:

1. database
2. backend CRUD APIs
3. analytics APIs
4. frontend screens
5. auth protection
6. filters and charts

### Phase 6. Testing

Validate:

- CRUD behavior
- authentication flow
- analytics calculations
- invalid input handling
- protected route behavior
- frontend to backend integration

### Phase 7. Submission Prep

Prepare:

- README
- setup guide
- screenshots
- source ZIP
- GitHub repository link
- project presentation summary

## Deployment Notes

### GitHub Pages Scope

- GitHub Pages is suitable for the React frontend only
- the Spring Boot backend must be deployed separately
- the frontend should read the backend URL from `VITE_API_BASE_URL`

### Repository Structure For Deployment

- `frontend/` contains the source for the static site
- `frontend/dist/` is the generated production build
- `.github/workflows/deploy-pages.yml` publishes the frontend to GitHub Pages

### Deployment Order

1. deploy backend
2. copy backend public API URL
3. add `VITE_API_BASE_URL` as a GitHub repository secret
4. push changes to `main`
5. let GitHub Actions publish the site

## Recommended Presentation Points

When presenting the project, emphasize:

- practical academic use case
- clean separation of frontend and backend
- secure admin editing flow
- meaningful analytics beyond basic CRUD
- flexibility of H2 for demo and MySQL for full deployment
