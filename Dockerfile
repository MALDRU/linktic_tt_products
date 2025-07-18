### BUILD image
FROM alpine:3.17.3

# Update packages
RUN apk update && \
    apk add --upgrade apk-tools && \
    apk upgrade --available && \
    sync

ONBUILD RUN apk update && \
    apk add --upgrade apk-tools && \
    apk upgrade --available --ignore openjdk17 openjdk17-jmods openjdk17-demos openjdk17-doc java-common java-cacerts openjdk17-jre-headless openjdk17-jre openjdk17-jdk && \
    sync

# Install OpenJDK 17
RUN apk --no-cache add openjdk17 --repository=https://dl-cdn.alpinelinux.org/alpine/v3.17/community
ENV HOME /root
ENV LANG es_CO.UTF-8
ENV LC_ALL es_CO.UTF-8

RUN apk add tzdata

# COPY JAR
COPY "app/build/libs/*.jar" "/home/app/target/app.jar"
WORKDIR  /home/app/target/
RUN chmod 755 /home/app/target/

# RUN
EXPOSE 8080
ENTRYPOINT ["java","-Dserver.port=8080", "-jar", "/home/app/target/app.jar"]