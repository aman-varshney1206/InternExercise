package org.ado.workitemtracking.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.ado.common.types.BaseAbstractMethod;

import java.util.List;

/***
 * Field instance of a work item type.
 */
@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkItemTypeFieldInstance extends BaseAbstractMethod {

    @JsonProperty("allowedValues")
    private String[] allowedValues;

    @JsonProperty("alwaysRequired")
    private boolean alwaysRequired;

    @JsonProperty("defaultValue")
    private String defaultValue;

    @JsonProperty("dependentFields")
    private List<WorkItemFieldReference> dependentFields;

    @JsonProperty("helpText")
    private String helpText;

    @JsonProperty("name")
    private String name;

    @JsonProperty("referenceName")
    private String referenceName;

    @JsonProperty("url")
    private String url;


}