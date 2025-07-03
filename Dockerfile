FROM eclipse-temurin:21 AS builder
WORKDIR /builder
COPY gradle ./gradle
COPY src ./src
COPY build.gradle ./
COPY gradlew ./
COPY settings.gradle ./
RUN ./gradlew clean bootJar
RUN java -Djarmode=tools -jar build/libs/hello-spring-boot-0.0.1-SNAPSHOT.jar extract --layers --destination extracted

FROM eclipse-temurin:21
WORKDIR /application
COPY --from=builder /builder/extracted/dependencies/ ./
COPY --from=builder /builder/extracted/spring-boot-loader/ ./
COPY --from=builder /builder/extracted/snapshot-dependencies/ ./
COPY --from=builder /builder/extracted/application/ ./
ENTRYPOINT ["java", "-jar", "hello-spring-boot-0.0.1-SNAPSHOT.jar"]
