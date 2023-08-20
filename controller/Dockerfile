FROM eclipse-temurin:17-jdk-alpine as builder

WORKDIR /home/build

ADD ../* .

RUN ./graldew controller:bootJar -x test

FROM eclipse-termurin:17-jre-alpine

WORKDIR /home/app/controller

COPY --from=builder /home/build/controller/build/libs/*.jar ./app.jar

ENTRYPOINT ["exec", "java", "-jar", "app.jar"]