# Скрипт для локального запуска нагрузочного теста (без Docker)
# Сохранить как: run-load-test.ps1

param(
    [int]$Users = 10,
    [int]$RampUp = 30,
    [int]$Duration = 60,
    [string]$BaseUrl = "https://www.saucedemo.com"
)

Write-Host "🚀 Starting Saucedemo Load Test" -ForegroundColor Green
Write-Host "Parameters:" -ForegroundColor Cyan
Write-Host "  Users: $Users"
Write-Host "  Ramp-up (seconds): $RampUp"
Write-Host "  Duration (seconds): $Duration"
Write-Host "  Base URL: $BaseUrl"
Write-Host ""

# Проверить наличие Maven
if (-not (Get-Command mvn -ErrorAction SilentlyContinue)) {
    Write-Host "❌ Maven не найден! Пожалуйста, установите Maven." -ForegroundColor Red
    exit 1
}

# Проверить наличие Java
if (-not (Get-Command java -ErrorAction SilentlyContinue)) {
    Write-Host "❌ Java не найдена! Пожалуйста, установите Java." -ForegroundColor Red
    exit 1
}

# Очистить и собрать проект
Write-Host "📦 Building project..." -ForegroundColor Yellow
mvn clean install -DskipTests

if ($LASTEXITCODE -ne 0) {
    Write-Host "❌ Build failed!" -ForegroundColor Red
    exit 1
}

# Запустить Gatling тест
Write-Host ""
Write-Host "🔥 Running load test..." -ForegroundColor Yellow

$env:USERS = $Users
$env:RAMP_UP = $RampUp
$env:DURATION = $Duration
$env:BASE_URL = $BaseUrl

# Compile and run using exec-maven-plugin with proper test classpath
mvn clean test-compile exec:java@run-simulation

if ($LASTEXITCODE -eq 0) {
    Write-Host ""
    Write-Host "✅ Load test completed!" -ForegroundColor Green
    Write-Host "📊 Results available in: target/gatling/" -ForegroundColor Cyan
} else {
    Write-Host "❌ Load test failed!" -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "✅ Load test completed!" -ForegroundColor Green
Write-Host "📊 Results available in: target/gatling/" -ForegroundColor Cyan

# Попытка открыть HTML отчет
$reportPath = Get-ChildItem -Path "target/gatling" -Filter "index.html" -Recurse | Select-Object -First 1
if ($reportPath) {
    Write-Host ""
    Write-Host "📈 Opening report..." -ForegroundColor Cyan
    Start-Process $reportPath.FullName
}
