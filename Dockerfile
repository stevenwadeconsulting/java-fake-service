FROM gradle:6.1.1-jdk13 as builder
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle --no-daemon quarkusBuild --uber-jar

FROM openjdk:13.0.2-slim

ARG git_repository="Unknown"
ARG git_commit="Unknown"
ARG git_branch="Unknown"
ARG built_on="Unknown"

LABEL git.repository=$git_repository
LABEL git.commit=$git_commit
LABEL git.branch=$git_branch
LABEL build.on=$built_on

WORKDIR /
COPY --from=builder /home/gradle/src/build/java-fake-service-1.0.0-SNAPSHOT-runner.jar .
RUN chmod +x java-fake-service-1.0.0-SNAPSHOT-runner.jar
EXPOSE 8080
CMD ["java","-jar","java-fake-service-1.0.0-SNAPSHOT-runner.jar"]