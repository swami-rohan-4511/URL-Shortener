# Dockerfile for Render deployment
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the Maven wrapper and pom.xml
COPY mvnw .
COPY .mvn .mvn
COPY .mvn/wrapper/maven-wrapper.jar .mvn/wrapper/
COPY pom.xml .

# Make mvnw executable
RUN chmod +x mvnw

# Download dependencies
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application
RUN ./mvnw clean package -DskipTests

# Expose the application port
EXPOSE 8080

# Set JVM options for Render
ENV JAVA_OPTS="-Xms256m -Xmx512m -XX:+UseG1GC"

# Run the application
CMD ["sh", "-c", "java $JAVA_OPTS -jar target/*.jar"]
