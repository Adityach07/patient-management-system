FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk AS runner

WORKDIR /app

# Difference 1: Copy the analytics service jar instead of billing
COPY --from=builder /app/target/analytics-service-0.0.1-SNAPSHOT.jar app.jar

# Difference 2: Expose only port 5000 (analytics port)
EXPOSE 5000

ENTRYPOINT ["java", "-jar", "app.jar"]