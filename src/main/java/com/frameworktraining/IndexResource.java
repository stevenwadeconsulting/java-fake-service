package com.frameworktraining;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

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

    private static final String ENV_VAR_SUFFIX = "training_";

    @Inject
    Template home;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get() {
        Map<String, String> environment = System.getenv();

        LinkedHashMap<String, String> trainingEnvironment = environment.entrySet().stream()
                .filter(this::hasSuffix)
                .sorted(Map.Entry.comparingByKey())
                .collect(toMapDoNotOverwrite());

        return home.data("env_vars", trainingEnvironment);
    }

    private boolean hasSuffix(Map.Entry<String, String> v) {
        return v.getKey().toLowerCase().startsWith(ENV_VAR_SUFFIX);
    }

    private Collector<Map.Entry<String, String>, ?, LinkedHashMap<String, String>> toMapDoNotOverwrite() {
        return Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                (oldValue, newValue) -> oldValue, LinkedHashMap::new);
    }
}