FROM eclipse-temurin:17-jdk-alpine as builder

WORKDIR /home/build

COPY ../common common
COPY ../controller controller
COPY ../gradle gradle
COPY ../build.gradle.kts build.gradle.kts
COPY ../gradlew gradlew
COPY ../settings.gradle.kts settings.gradle.kts

RUN ./graldew controller:bootJar -x test

FROM eclipse-termurin:17-jre-alpine

WORKDIR /home/app/controller

COPY --from=builder /home/build/controller/build/libs/*.jar ./app.jar

ENTRYPOINT ["exec", "java", "-jar", "app.jar"]