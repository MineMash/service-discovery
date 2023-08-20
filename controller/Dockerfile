FROM eclipse-temurin:17-jdk-alpine as builder

WORKDIR /home/build

COPY ../common common
COPY ../controller controller
COPY ../gradle gradle
COPY ../build.gradle.kts build.gradle.kts
COPY ../gradlew gradlew
COPY ../settings.gradle.kts settings.gradle.kts

RUN ./graldew clean controller:bootJar -x test

# Extract layers for faster application startup
ARG BUILD_FOLDER=controller/build
RUN mkdir -p $BUILD_FOLDER/dependency && (cd $BUILD_FOLDER/dependency; jar -xf ../*.jar)

FROM eclipse-termurin:17-jre-alpine

WORKDIR /home/app/controller
RUN addgroup -S runner && adduser -S runner -G runner

# Copy extracted layers
ARG DEPENDENCY=/home/build/controller/build/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib ./lib
COPY --from=build ${DEPENDENCY}/META-INF ./META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes .

# run as a non-root user
USER runner

ARG MAIN_CLASS="dev.minemesh.controller.ControllerApplication"
ENTRYPOINT ["java", "-cp", "app:lib/*", "$MAIN_CLASS"]