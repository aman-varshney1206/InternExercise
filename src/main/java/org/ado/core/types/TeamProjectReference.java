package org.ado.core.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.ado.common.types.BaseAbstractMethod;

/***
 * Represents a shallow reference to a TeamProject.
 */
@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamProjectReference extends BaseAbstractMethod {

    @JsonProperty("abbreviation")
    private String abbreviation;

    @JsonProperty("defaultTeamImageUrl")
    private String defaultTeamImageUrl;

    @JsonProperty("description")
    private String description;

    @JsonProperty("id")
    private String id;

    @JsonProperty("lastUpdateTime")
    private String lastUpdateTime;

    @JsonProperty("name")
    private String name;

    @JsonProperty("revision")
    private String revision;

    @JsonProperty("state")
    private String state;

    @JsonProperty("url")
    private String url;

    @JsonProperty("visibility")
    private String visibility;

}