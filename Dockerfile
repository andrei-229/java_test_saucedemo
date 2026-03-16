# Build and test stage
FROM maven:3.9.0-eclipse-temurin-11

WORKDIR /app

# Копируем pom.xml и скачиваем зависимости
COPY pom.xml .
RUN mvn dependency:resolve -DskipTests

# Копируем исходный код
COPY src ./src

# Собираем проект
RUN mvn clean install -DskipTests

# Переменные по умолчанию
ENV USERS=10
ENV RAMP_UP=30
ENV DURATION=60
ENV BASE_URL=https://www.saucedemo.com

# Команда для запуска нагрузочного теста
ENTRYPOINT ["mvn", "clean", "-Dmaven.clean.failOnError=false", "test-compile", "exec:java@run-simulation"]
