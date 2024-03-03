package org.ado.enums;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Use ExtensionFields to include extension fields, otherwise exclude them. Unless the feature flag for this parameter is enabled, extension fields are always included.
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
public enum GetFieldsExpand {

    @JsonProperty("extensionFields")
    EXTENSIONFIELDS,

    @JsonProperty("includeDeleted")
    INCLUDEDELETED,

    @JsonProperty("none")
    NONE;
}