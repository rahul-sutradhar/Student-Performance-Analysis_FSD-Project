$projectRoot = Split-Path -Parent $MyInvocation.MyCommand.Path
$backendPath = Join-Path $projectRoot "backend"
$frontendPath = Join-Path $projectRoot "frontend"

Write-Host "Starting Student Performance Analysis locally..." -ForegroundColor Cyan

if (-not (Get-Command npm -ErrorAction SilentlyContinue)) {
    Write-Error "npm is not installed or not available in PATH."
    exit 1
}

if (-not (Get-Command mvn -ErrorAction SilentlyContinue)) {
    Write-Error "Maven is not installed or not available in PATH."
    Write-Host "Install Maven, then run this script again." -ForegroundColor Yellow
    exit 1
}

Start-Process powershell -ArgumentList "-NoExit", "-Command", "Set-Location -LiteralPath '$backendPath'; mvn spring-boot:run -Dspring-boot.run.profiles=dev"
Start-Process powershell -ArgumentList "-NoExit", "-Command", "Set-Location -LiteralPath '$frontendPath'; npm run dev"

Write-Host "Backend starting on http://localhost:8080" -ForegroundColor Green
Write-Host "Frontend starting on http://localhost:5173" -ForegroundColor Green
