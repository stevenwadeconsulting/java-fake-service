package com.frameworktraining;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

import javax.enterprise.context.ApplicationScoped;

@Readiness
@ApplicationScoped
public class ReadinessHealthCheck implements HealthCheck {

    @ConfigProperty(name = "readiness.healthy", defaultValue = "true")
    boolean healthy;

    @Override
    public HealthCheckResponse call() {
        return healthy ?
                HealthCheckResponse.up("Application is ready to serve traffic!") :
                HealthCheckResponse.down("Application is not ready to serve traffic!");
    }

}
