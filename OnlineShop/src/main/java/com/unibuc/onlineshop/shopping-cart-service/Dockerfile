FROM amazoncorretto:11-alpine3.16

EXPOSE 8080

WORKDIR /usr/local/bin/

COPY ./target/shopping-cart-service-0.0.1-SNAPSHOT.jar webapp.jar

CMD ["java", "-jar", "webapp.jar"]