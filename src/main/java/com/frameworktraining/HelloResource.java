package com.frameworktraining;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Path("/greeting")
public class HelloResource {

    private static final String DEFAULT_NAME = "World";

    @ConfigProperty(name = "greeting.name")
    Optional<String> name;

    @ConfigProperty(name = "greeting.availableNames", defaultValue = DEFAULT_NAME)
    String availableNames;

    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    @Counted(name = "hello.count", description = "A counter for how many times the hello resource is requested.")
    @Timed(name = "hello.timer", description = "A measure of how many milliseconds it takes to serve a request on the hello resource.", unit = MetricUnits.MILLISECONDS)
    public String hello() {
        return "Hello " + name.orElse(DEFAULT_NAME);
    }

    @GET
    @Path("/names")
    @Produces(MediaType.APPLICATION_JSON)
    @Counted(name = "names.count", description = "A counter for how many times the available names resource is requested.")
    @Timed(name = "names.timer", description = "A measure of how many milliseconds it takes to serve a request on the available names resource.", unit = MetricUnits.MILLISECONDS)
    public String availableNames() {
        Random rand = new Random();
        List<String> names = Arrays.asList(availableNames.split(","));
        return names.get(rand.nextInt(names.size()));
    }

}