package org.ado.core.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.ado.common.types.BaseAbstractMethod;

;

/***
 * Represents a team
 */

@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WebApiTeam extends BaseAbstractMethod {

    @JsonProperty("description")
    private String description;

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("identityUrl")
    private String identityUrl;

    @JsonProperty("url")
    private String url;



}