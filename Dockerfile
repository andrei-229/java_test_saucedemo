# Build stage
FROM maven:3.9.0-eclipse-temurin-11 as builder

WORKDIR /app

# Копируем pom.xml и скачиваем зависимости
COPY pom.xml .
RUN mvn dependency:resolve -DskipTests

# Копируем исходный код
COPY src ./src

# Собираем проект
RUN mvn clean install -DskipTests

# Runtime stage
FROM eclipse-temurin:11-jre-alpine

WORKDIR /app

# Копируем собранный проект из builder
COPY --from=builder /app/target ./target
COPY --from=builder /app/src ./src
COPY --from=builder /app/pom.xml .
COPY --from=builder /root/.m2 /root/.m2

# Устанавливаем Maven в runtime образ
RUN apk add --no-cache maven

# Переменные по умолчанию
ENV USERS=10
ENV RAMP_UP=30
ENV DURATION=60
ENV BASE_URL=https://www.saucedemo.com

# Команда для запуска нагрузочного теста
ENTRYPOINT ["mvn", "clean", "test-compile", "exec:java@run-simulation"]
