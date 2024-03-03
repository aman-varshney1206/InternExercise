package com.demo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter @Setter
public class Configuration {
    private String systemName;

    @JsonProperty("source")
    private ConfigDetails source;

    @JsonProperty("target")
    private ConfigDetails target;

    @JsonProperty("mappings")
    private Mappings mappings;
}

@Getter @Setter
class ConfigDetails {
    private String organisation;
    private String apiUrl;
    private ProjectDetails project;
    private AuthDetails auth;

}

@Getter @Setter
class ProjectDetails {
    private String id;
    private String name;

}

@Getter @Setter
class AuthDetails {
    private String username;
    private String pat;

}

@Getter @Setter
class Mappings {
    @JsonProperty("entityMappings")
    private Map<String, String> entityMappings;

    @JsonProperty("fieldMappings")
    private Map<String, Map<String, String>> fieldMappings;

    @JsonProperty("valueMappings")
    private Map<String, Map<String, Map<String, String>>> valueMappings;

}

