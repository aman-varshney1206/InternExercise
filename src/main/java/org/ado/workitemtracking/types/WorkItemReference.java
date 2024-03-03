package org.ado.workitemtracking.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.ado.common.types.BaseAbstractMethod;

/***
 * Contains reference to a work item.
 */
@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkItemReference extends BaseAbstractMethod {

    @JsonProperty("id")
    private int id;

    @JsonProperty("url")
    private String url;

}