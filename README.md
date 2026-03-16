# java_test_saucedemo

Комплексное решение для автоматизации тестирования [Saucedemo](https://www.saucedemo.com/) веб-приложения.

## 📋 Компоненты проекта

- **UI Тесты** - Selenium + JUnit 5 + Page Object Model
- **API Тесты** - REST Assured с проверкой JSON
- **Нагрузочное тестирование** - Gatling с Docker поддержкой
- **Отчеты** - Allure Reports для UI/API, HTML для Gatling

## 🚀 Быстрый старт

### Требования
- Java 11+
- Maven 3.6+
- Docker (опционально)

### Запуск тестов

```bash
# Установка зависимостей
mvn clean install

# UI тесты
mvn test -P ui-tests

# API тесты  
mvn test -P api-tests

# Нагрузочное тестирование (Windows)
.\run-load-test.ps1

# Нагрузочное тестирование (Linux/Mac)
./run-load-test.sh

# Через Docker
docker-compose up --build
```

## 📁 Структура проекта

```
src/test/
├── java/
│   ├── ui/                  # UI тесты (Selenium)
│   │   ├── base/           # Базовые классы (WebDriver)
│   │   ├── pages/          # Page Object Model
│   │   └── tests/          # Тестовые классы
│   ├── api/                 # API тесты (REST Assured)
│   │   ├── base/           # Базовые классы
│   │   ├── methods/        # API методы
│   │   └── tests/          # Тестовые классы
│   └── utils/              # Утилиты
└── scala/
    └── loadtesting/        # Gatling сценарии
```

⚙️ Параметры нагрузочного тестирования

Параметры задаются через переменные окружения или флаги PowerShell:

```powershell
# С флагами PowerShell (рекомендуется)
.\run-load-test.ps1 -Users 50 -RampUp 60 -Duration 300

# Или через переменные окружения
$env:USERS = 50
$env:RAMP_UP = 60
$env:DURATION = 300
$env:BASE_URL = "https://www.saucedemo.com"
.\run-load-test.ps1
```

| Параметр | Описание | Default |
|----------|---------|---------|
| `USERS` | Количество виртуальных пользователей | 10 |
| `RAMP_UP` | Время разогрева (секунды) | 30 |
| `DURATION` | Длительность теста (секунды) | 60 |
| `BASE_URL` | URL приложения | https://www.saucedemo.com |

## 📊 Результаты

### Allure отчеты
```bash
mvn allure:serve
```

### Gatling отчеты

Результаты сохраняются в `target/gatling/` директорию:

```
target/gatling/
├── gatling-YYYYMMDDHHMMSS/
│   ├── index.html         # Главная страница отчета
│   ├── simulation.log      # Лог данные
│   └── js, css, images/   # Ресурсы отчета
```

Откройте `index.html` в браузере для просмотра интерактивного отчета.

**Метрики:** Response Time, Success Rate, RPS, Percentiles (p95, p99)

## 🐳 Docker

```bash
# Сборка
docker build -t saucedemo-load-test .

# Docker Compose запуск
docker-compose up --build

# С параметрами
USERS=100 DURATION=300 docker-compose up --build
```

## 💡 Лучшие практики

**UI:** Page Object Model, явные/неявные ожидания, данные-управляемые тесты

**API:** Проверка кодов ответов, валидация JSON, логирование запросов

**Load:** Реалистичные сценарии, pauses между действиями, анализ p95/p99

## 📖 Использованные версии

- Selenium 4.15.0
- REST Assured 5.4.0  
- Gatling 3.9.5
- JUnit 5.10.0
- Allure 2.24.0
- Maven Surefire 3.2.5

## 🔗 Документация

- Saucedemo: https://www.saucedemo.com/
- Selenium: https://selenium.dev/
- Gatling: https://gatling.io/
- Allure: https://docs.qameta.io/allure/

---

**Версия:** 2.0 | **Обновлено:** March 2026 | **Лицензия:** MIT
