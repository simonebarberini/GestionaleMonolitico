# Usa l'immagine di base di OpenJDK per eseguire Spring Boot
FROM openjdk:17-jdk-slim

# Imposta la directory di lavoro nel container
WORKDIR /app

# Copia l'applicazione Spring Boot buildata
COPY target/backend-0.0.1-SNAPSHOT.jar backend.jar

# Esponi la porta del backend
EXPOSE 8081

# Comando per avviare il backend
CMD ["java", "-jar", "backend.jar"]
