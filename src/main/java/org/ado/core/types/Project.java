package org.ado.core.types;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.ado.common.types.BaseAbstractMethod;
import org.ado.enums.ProjectState;
import org.ado.enums.ProjectVisibility;

/***
 * Represents a Team Project object.
 */
@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Project extends BaseAbstractMethod {

    @JsonProperty("_links")
    private Object _links;

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("url")
    private String url;

    @JsonProperty("state")
    private ProjectState state;

    @JsonProperty("capabilities")
    private Capabilities capabilities;

    @JsonProperty("revision")
    private int revision;

    @JsonProperty("visibility")
    private ProjectVisibility visibility;

    @JsonProperty("defaultTeam")
    private Team defaultTeam;

    @JsonProperty("lastUpdateTime")
    private String lastUpdateTime;

}