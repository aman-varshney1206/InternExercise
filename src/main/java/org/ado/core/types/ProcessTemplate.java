package org.ado.core.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.ado.common.types.BaseAbstractMethod;


@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProcessTemplate extends BaseAbstractMethod {
    @JsonProperty("templateName")
    private String templateName;
    @JsonProperty("templateTypeId")
    private String templateTypeId;

}