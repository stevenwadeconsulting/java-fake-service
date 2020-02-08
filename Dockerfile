FROM gradle:6.1.1-jdk13 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN ls -lart

RUN ./gradlew
