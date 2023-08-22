FROM gradle:8.3.0-jdk17 as builder

WORKDIR /home/build

COPY build.gradle.kts settings.gradle.kts ./
COPY controller controller
COPY common common
# COPY gradle/versions.toml .

RUN gradle clean controller:bootJar -x test

# Extract layers for faster application startup
ARG BUILD_FOLDER=controller/build
RUN mkdir -p $BUILD_FOLDER/dependency && (cd $BUILD_FOLDER/dependency; ls ../libs; jar -xf ../libs/*.jar)

FROM eclipse-temurin:17-jre

WORKDIR /home/app/controller
RUN addgroup --system runner && adduser --system runner --ingroup runner

# Copy extracted layers
ARG DEPENDENCY=/home/build/controller/build/dependency
COPY --from=builder ${DEPENDENCY}/BOOT-INF/lib ./lib
COPY --from=builder ${DEPENDENCY}/META-INF ./META-INF
COPY --from=builder ${DEPENDENCY}/BOOT-INF/classes ./app

# run as a non-root user
USER runner

ARG MAIN_CLASS="dev.minemesh.controller.ControllerApplication"
ENV MAIN_CLASS_ENV=$MAIN_CLASS
ENTRYPOINT sleep 30 && exec java -cp app:lib/* $MAIN_CLASS_ENV