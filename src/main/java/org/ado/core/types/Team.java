package org.ado.core.types;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.ado.common.types.BaseAbstractMethod;

/***
 * Represents a project team
 */

@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Team extends BaseAbstractMethod {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("url")
    private String url;

    @JsonProperty("description")
    private String description;

    @JsonProperty("identityUrl")
    private String identityUrl;

    @JsonProperty("projectName")
    private String projectName;

    @JsonProperty("projectId")
    private String projectId;

}