package org.ado.workitemtracking.types;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.ado.common.types.BaseAbstractMethod;

import java.util.List;

@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkItemFieldTypes extends BaseAbstractMethod {
    @JsonProperty("value")
    private List<WorkItemField> workItemFields;

}