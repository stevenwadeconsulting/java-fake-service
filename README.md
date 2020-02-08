# java-fake-service

This is a fake Java service built using the Quarkus framework.

It has two main endpoints:
| Endpoint | Content Type |
| --- | --- |
| `/` | HTML |
| `/hello` | PLAINTEXT |
| `/health/live` | JSON |
| `/health/ready` | JSON |

## `/`

This endpoint returns a html page containing application information and will list any environment variables that start with `TRAINING_`.

```bash
TRAINING_VARIABLE=test java -jar build/java-fake-service-1.0.0-runner.jar
```

## `/hello`

This endpoint will return `Hello <insert name here>` by default. If you want to override the name you can provide an `application.properties` file under `$PWD/config/application.properties` where `$PWD` is the location of the jar file.

If you want to override set the following property in the `application.properties`:

```properties
greeting.name = Another name!
```

## `/health/live`

This is a health check to confirm that the application is up and running. You can toggle this to fail via a property in `application.properties`:

```properties
liveness.healthy = true
```

When failing it will return a `503` status code.

## `/health/ready`

This is a health check to confirm that the application is ready to serve requests. You can toggle this to fail via a property in `application.properties`:
                                                                                   
```properties
readiness.healthy = true
```

When failing it will return a `503` status code.
