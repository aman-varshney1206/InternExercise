package org.ado.core.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.ado.common.types.BaseAbstractMethod;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Processes extends BaseAbstractMethod {
    @JsonProperty("value")
    private List<java.lang.Process> processes;

}