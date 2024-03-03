package org.ado.enums;
/**
 ----------------------------------------------------------
 GENERATED FILE, should be edited to suit the purpose.
 ----------------------------------------------------------
 **/

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The usage of the field.
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
public enum FieldUsage {

    @JsonProperty("none")
    NONE,

    @JsonProperty("tree")
    TREE,

    @JsonProperty("workItem")
    WORKITEM,

    @JsonProperty("workItemLink")
    WORKITEMLINK,

    @JsonProperty("workItemTypeExtension")
    WORKITEMTYPEEXTENSION;
}