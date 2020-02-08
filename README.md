# java-fake-service

This is a fake Java service built using the Quarkus framework.

It has two main endpoints:

- `/` - HTML
- `/hello` - PLAINTEXT

The first endpoint returns a html page containing application information and will list any environment variables that start with `TRAINING_`.

```bash
TRAINING_VARIABLE=test java -jar build/java-fake-service-1.0.0-runner.jar
```

The second endpoint will return `Hello Quarkus!` by default. If you want to override the name you can provide an `application.properties` file under `$PWD/config/application.properties` where `$PWD` is the location of the jar file.

If you want to override set the following property in the `application.properties`:

```properties
greeting.name = Another name!
```
