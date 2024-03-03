package org.ado;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Configuration {
    @JsonProperty("systemName")
    private String systemName;

    @JsonProperty("source")
    private AzureDevOpsConfig source;

    @JsonProperty("target")
    private AzureDevOpsConfig target;

}


@Getter @Setter
class AzureDevOpsConfig {
    @JsonProperty("organisation")
    private String organisation;

    @JsonProperty("apiUrl")
    private String apiUrl;

    @JsonProperty("project")
    private ProjectConfig project;

    @JsonProperty("auth")
    private AuthConfig auth;


}


@Getter @Setter
class ProjectConfig {
    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

}

@Getter @Setter
class AuthConfig {
    @JsonProperty("username")
    private String username;

    @JsonProperty("pat")
    private String pat;
}
