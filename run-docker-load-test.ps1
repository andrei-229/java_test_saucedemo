# Scripts for running load tests

## PowerShell скрипт для запуска через Docker
# Сохранить как: run-docker-load-test.ps1

param(
    [int]$Users = 10,
    [int]$RampUp = 30,
    [int]$Duration = 60,
    [string]$BaseUrl = "https://www.saucedemo.com"
)

Write-Host "🚀 Starting Saucedemo Load Test with Docker" -ForegroundColor Green
Write-Host "Parameters:" -ForegroundColor Cyan
Write-Host "  Users: $Users"
Write-Host "  Ramp-up (seconds): $RampUp"
Write-Host "  Duration (seconds): $Duration"
Write-Host "  Base URL: $BaseUrl"
Write-Host ""

# Построить Docker образ
Write-Host "📦 Building Docker image..." -ForegroundColor Yellow
docker build -t saucedemo-load-test .

if ($LASTEXITCODE -ne 0) {
    Write-Host "❌ Docker build failed!" -ForegroundColor Red
    exit 1
}

# Запустить контейнер с параметрами
Write-Host "🔥 Running load test..." -ForegroundColor Yellow
docker run --rm `
    -e USERS=$Users `
    -e RAMP_UP=$RampUp `
    -e DURATION=$Duration `
    -e BASE_URL=$BaseUrl `
    -v "${PWD}/target/gatling:/app/target/gatling" `
    saucedemo-load-test

Write-Host ""
Write-Host "✅ Load test completed!" -ForegroundColor Green
Write-Host "📊 Results available in: target/gatling/" -ForegroundColor Cyan
