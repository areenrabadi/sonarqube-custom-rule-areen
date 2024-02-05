# Docker file for the Read Service
#
# Version 0.0.1

#jdk image
FROM openjdk:21-jdk-slim

EXPOSE 9097
# install

# label for the image
LABEL Description="attachment-service" Version="0.0.1"

# the version of the archive
ARG VERSION=0.0.1

# mount the temp volume
VOLUME /tmp

# Add the service as app.jar
ADD target/attachment-service-${VERSION}-SNAPSHOT.jar attachment-service.jar

# touch the archive for timestamp
RUN sh -c 'touch /attachment-service.jar'

# entrypoint to the image on run
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/attachment-service.jar"]
