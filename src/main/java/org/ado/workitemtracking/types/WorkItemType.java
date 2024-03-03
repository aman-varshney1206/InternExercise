package org.ado.workitemtracking.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;
import org.ado.common.types.BaseAbstractMethod;

import java.util.List;

/***
 * Describes a work item type.
 */
@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkItemType extends BaseAbstractMethod {

    @JsonProperty("color")
    private String color;

    @JsonProperty("description")
    private String description;

    @JsonProperty("fieldInstances")
    private List<WorkItemTypeFieldInstance> fieldInstances;

    @JsonProperty("fields")
    private List<WorkItemTypeFieldInstance> fields;

    @JsonProperty("icon")
    private WorkItemIcon icon;

    @JsonProperty("isDisabled")
    private boolean isDisabled;

    @JsonProperty("name")
    private String name;

    @JsonProperty("referenceName")
    private String referenceName;

    @JsonProperty("states")
    private List<WorkItemStateColor> states;

    @JsonProperty("transitions")
    private JsonNode transitions;

    @JsonProperty("url")
    private String url;

    @JsonProperty("xmlForm")
    private String xmlForm;

}