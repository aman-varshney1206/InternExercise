package org.ado.workitemtracking.types;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.ado.common.types.BaseAbstractMethod;

/***
 * Work item type state name, color and state category
 */
@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkItemStateColor extends BaseAbstractMethod {

    @JsonProperty("category")
    private String category;

    @JsonProperty("color")
    private String color;

    @JsonProperty("name")
    private String name;

}