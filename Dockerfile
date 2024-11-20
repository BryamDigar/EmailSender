# Fase de construcción (Build stage)
FROM eclipse-temurin:21-jdk AS build

# Instalar dependencias necesarias para la compilación
RUN apt-get update && apt-get install -y \
    git \
    wget

# Copiar el código fuente al contenedor
COPY . .

# Dar permisos de ejecución al archivo gradlew
RUN chmod +x ./gradlew

# Ejecutar Gradle para generar el archivo .jar
RUN ./gradlew bootJar --no-daemon

# Fase de ejecución (Run stage)
FROM eclipse-temurin:21-jdk

# Exponer el puerto donde corre la aplicación
EXPOSE 8080

# Copiar el archivo .jar generado desde la fase de construcción
COPY --from=build /build/libs/email-0.0.1-SNAPSHOT.jar app.jar

# Configurar variables de entorno
ENV EMAIL=${EMAIL}
ENV PASSWORD_EMAIL=${PASSWORD_EMAIL}
ENV RABBITMQ_HOST=${RABBITMQ_HOST}
ENV RABBITMQ_USER=${RABBITMQ_USER}
ENV RABBITMQ_PASSWORD=${RABBITMQ_PASSWORD}

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]