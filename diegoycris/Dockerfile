# ---- Etapa 1: build ----
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /build

# Cacheamos dependencias primero (si el pom.xml no cambia, Docker reutiliza esta capa)
COPY pom.xml .
RUN mvn -B -q dependency:go-offline

# Copiamos el código y compilamos
COPY src ./src
RUN mvn -B -q clean package -DskipTests

# ---- Etapa 2: runtime ----
FROM eclipse-temurin:21-jre-alpine

# Usuario no root por seguridad
RUN addgroup -S app && adduser -S app -G app

# El jar vive aquí...
WORKDIR /app
COPY --from=build /build/target/*.jar app.jar

# ...pero se ejecuta con cwd en /data, para que el CSV de RSVPs
# (respuestas-rsvp.csv, ruta relativa) se escriba ahí y persista en un volumen
RUN mkdir -p /data && chown -R app:app /app /data
WORKDIR /data
USER app

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
