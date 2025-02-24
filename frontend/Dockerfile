# Usa l'immagine di base di OpenJDK per eseguire l'app Spring Boot
FROM openjdk:17-jdk-slim

# Imposta la directory di lavoro nel container
WORKDIR /app

# Copia l'app Spring Boot buildata
COPY target/frontend-0.0.1-SNAPSHOT.jar frontend.jar

# Esponi la porta 8082 per il frontend
EXPOSE 8082

# Comando per avviare il frontend
CMD ["java", "-jar", "frontend.jar"]
