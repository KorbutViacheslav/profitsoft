# Використовуємо базовий образ Java
FROM openjdk:17-jdk-slim

# Встановлюємо робочий каталог всередині контейнера
WORKDIR /app

# Копіюємо весь вихідний код Java додатку
COPY . .

# Компілюємо Java додаток
RUN ./mvnw clean package

# Вказуємо команду для запуску додатку
CMD ["java", "-jar", "target/email-service.jar"]