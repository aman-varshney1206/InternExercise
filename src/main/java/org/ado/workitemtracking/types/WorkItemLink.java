package org.ado.workitemtracking.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.ado.common.types.BaseAbstractMethod;

/***
 * A link between two work items.
 */
@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkItemLink extends BaseAbstractMethod {

    @JsonProperty("rel")
    private String rel;

    @JsonProperty("source")
    private WorkItemReference source;

    @JsonProperty("target")
    private WorkItemReference target;
}