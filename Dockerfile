# Docker file for the Read Service
#
# Version 0.0.1

#jdk image
FROM openjdk:21-jdk-slim

# install

# label for the image
LABEL Description="sonarqube plugin" Version="1.0" Author="Areen Rabadi"

# the version of the archive
ARG VERSION=1.0

# mount the temp volume
VOLUME /tmp

# Add the service as app.jar
ADD target/sonarqube-custom-rule-areen-${VERSION}-SNAPSHOT.jar sonarqube-custom-rule-areen.jar
