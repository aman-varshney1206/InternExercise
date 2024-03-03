package org.ado.core.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.ado.common.types.BaseAbstractMethod;

@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class VersionControl extends BaseAbstractMethod {
    @JsonProperty("sourceControlType")
    private String sourceControlType;
    @JsonProperty("gitEnabled")
    private boolean gitEnabled;
    @JsonProperty("tfvcEnabled")
    private boolean tfvcEnabled;

}