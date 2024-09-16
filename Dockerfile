FROM gradle:8.10.0-jdk21 as builder

WORKDIR /build

COPY build.gradle /build
COPY gradle.properties /build
COPY src /build/src
COPY settings.gradle /build


RUN gradle shadowJar
RUN ls -l /build/build/libs/

FROM openjdk:21
COPY --from=builder "/build/build/libs/Stalker-1.0.0-all.jar" Stakler-1.0.0-all.jar

CMD ["java", "-jar", "Stakler-1.0.0-all.jar"]