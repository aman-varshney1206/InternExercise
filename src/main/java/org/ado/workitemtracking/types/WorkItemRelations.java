package org.ado.workitemtracking.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/***
 * Relations of the work item.
 */
@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public final class WorkItemRelations {

    @JsonProperty("rel")
    private String rel;

    @JsonProperty("url")
    private String url;

    @JsonProperty("attributes")
    private WorkItemAttributes attributes;

}