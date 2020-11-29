FROM openjdk:8u252
MAINTAINER "dazhi"
LABEL version="0.0.1"
LABEL description="使用gateway作为网关服务"
WORKDIR /data/service
VOLUME /tmp
ADD target/wz-gateway.jar /data/service/gateway.jar
EXPOSE 8000
CMD ["java", "-jar", "/data/service/gateway.jar"]