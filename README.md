# java-fake-service

This is a fake Java service built using the Quarkus framework.

It has two main endpoints:
| Endpoint | Content Type |
| --- | --- |
| `/` | HTML |
| `/greeting/hello` | PLAINTEXT |
| `/greeting/names` | PLAINTEXT |
| `/health/live` | JSON |
| `/health/ready` | JSON |
| `/metrics` | PLAINTEXT |

## `/`

This endpoint returns a html page containing application information and will list any environment variables that start with `TRAINING_`.

```bash
TRAINING_VARIABLE=test java -jar build/java-fake-service-1.0.0-runner.jar
```

There is also another environment variable to control what is displayed in the banner at the top of the page. `INSTANCE_NAME` will let you override the default value of `Java Fake Service`.

## `/greeting/hello`

This endpoint will return `Hello <insert name here>` by default. If you want to override the name you can provide an `application.properties` file under `$PWD/config/application.properties` where `$PWD` is the location of the jar file.

If you want to override set the following property in the `application.properties`:

```properties
greeting.name = Another name!
```

## `/greeting/names`

This endpoint will return a random name from a list of names provided via the `greeting.availableNames` key in `application.properties`. If this is not set it defaults to `World`.

```properties
greeting.availableNames=Jen,Steve,Will,Tom,Vicky,Dave
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

## `/metrics`

This is an endpoint to expose metrics in a plaintext format. The two exposed resources have both a `count` and a `timer` metrics to provide request latency metrics.
