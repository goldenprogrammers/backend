FROM eclipse-temurin
WORKDIR /app
 
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw && ./mvnw dependency:go-offline
 
COPY src ./src

RUN chmod -R 777 /app
EXPOSE 8080

CMD ["./mvnw", "spring-boot:run"]