package org.ado.workitemtracking.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.ado.common.types.BaseAbstractMethod;

/***
 * Reference to a field in a work item
 */
@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkItemFieldReference extends BaseAbstractMethod {

    @JsonProperty("name")
    private String name;

    @JsonProperty("referenceName")
    private String referenceName;

    @JsonProperty("url")
    private String url;

}