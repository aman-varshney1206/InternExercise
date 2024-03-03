package org.ado.workitemtracking.types;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.ado.common.types.BaseAbstractMethod;

/**
 * Describes a work item field operation.
 **/
@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkItemFieldOperation extends BaseAbstractMethod {

    @JsonProperty("name")
    private String name;

    @JsonProperty("referenceName")
    private String referenceName;

}