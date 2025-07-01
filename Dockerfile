FROM eclipse-temurin:21
WORKDIR /hello-spring-boot
COPY gradle /hello-spring-boot/gradle
COPY src /hello-spring-boot/src
COPY build.gradle /hello-spring-boot/
COPY gradlew /hello-spring-boot/
COPY settings.gradle /hello-spring-boot/
RUN ./gradlew clean bootJar
ENTRYPOINT ["java", "-jar", "build/libs/hello-spring-boot-0.0.1-SNAPSHOT.jar"]
