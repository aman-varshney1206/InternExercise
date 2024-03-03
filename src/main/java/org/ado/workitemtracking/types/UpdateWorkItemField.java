package org.ado.workitemtracking.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.ado.common.types.BaseAbstractMethod;

@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateWorkItemField extends BaseAbstractMethod {
    @JsonProperty("isDeleted")
    private boolean isDeleted;

}