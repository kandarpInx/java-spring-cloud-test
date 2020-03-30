FROM openjdk:11-slim
WORKDIR /app

#ARG consul_host
#ARG config_server_host

#ENV CONSUL_HOST ${consul_host}
#ENV CONFIG_SERVER_HOST ${config_server_host}

COPY ./target/terabizcloud-auth-0.0.1-SNAPSHOT.jar ./
CMD ["java", "-jar", "terabizcloud-auth-0.0.1-SNAPSHOT.jar"]
