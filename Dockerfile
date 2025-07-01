FROM eclipse-temurin:21
COPY build/libs/* /
ENTRYPOINT ["java", "-jar", "hello-spring-boot-0.0.1-SNAPSHOT.jar"]
