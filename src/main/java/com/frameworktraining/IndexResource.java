package com.frameworktraining;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Path("/")
public class IndexResource {

    private static final String DEFAULT_INSTANCE_NAME = "Java Fake Service";
    private static final String ENV_INSTANCE_NAME = "INSTANCE_NAME";
    private static final String ENV_VAR_SUFFIX = "training_";

    @Inject
    Template home;

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Counted(name = "count", description = "A counter for how many times the index resource is requested.")
    @Timed(name = "timer", description = "A measure of how many milliseconds it takes to serve a request on the index resource.", unit = MetricUnits.MILLISECONDS)
    public TemplateInstance get() {
        Map<String, String> environment = System.getenv();

        String applicationName = environment.getOrDefault(ENV_INSTANCE_NAME, DEFAULT_INSTANCE_NAME);

        LinkedHashMap<String, String> trainingEnvironment = environment.entrySet().stream()
                .filter(this::hasSuffix)
                .sorted(Map.Entry.comparingByKey())
                .collect(toMapDoNotOverwrite());

        return home
                .data("banner_name", applicationName)
                .data("env_vars", trainingEnvironment);
    }

    private boolean hasSuffix(Map.Entry<String, String> v) {
        return v.getKey().toLowerCase().startsWith(ENV_VAR_SUFFIX);
    }

    private Collector<Map.Entry<String, String>, ?, LinkedHashMap<String, String>> toMapDoNotOverwrite() {
        return Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                (oldValue, newValue) -> oldValue, LinkedHashMap::new);
    }
}