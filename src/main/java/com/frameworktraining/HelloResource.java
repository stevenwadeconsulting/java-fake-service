package com.frameworktraining;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Optional;

@Path("/hello")
public class HelloResource {

    @ConfigProperty(name = "greeting.name")
    Optional<String> name;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Counted(name = "count", description = "A counter for how many times the hello resource is requested.")
    @Timed(name = "timer", description = "A measure of how many milliseconds it takes to serve a request on the hello resource.", unit = MetricUnits.MILLISECONDS)
    public String hello() {
        return "Hello " + name.orElse("world!");
    }
}