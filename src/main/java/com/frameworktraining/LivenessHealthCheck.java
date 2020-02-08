package com.frameworktraining;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

import javax.enterprise.context.ApplicationScoped;

@Liveness
@ApplicationScoped
public class LivenessHealthCheck implements HealthCheck {

    @ConfigProperty(name = "liveness.healthy", defaultValue = "true")
    boolean healthy;

    @Override
    public HealthCheckResponse call() {
        return healthy ?
                HealthCheckResponse.up("Application is up!") :
                HealthCheckResponse.down("Application is down!");
    }

}
