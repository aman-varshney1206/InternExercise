package org.ado.enums;

/**
 ----------------------------------------------------------
 GENERATED FILE, should be edited to suit the purpose.
 ----------------------------------------------------------
 **/

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The type of the field.
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
public enum FieldType {
    @JsonProperty("boolean")
    BOOLEAN,

    @JsonProperty("dateTime")
    DATETIME,

    @JsonProperty("double")
    DOUBLE,

    @JsonProperty("guid")
    GUID,

    @JsonProperty("history")
    HISTORY,

    @JsonProperty("html")
    HTML,

    @JsonProperty("identity")
    IDENTITY,

    @JsonProperty("integer")
    INTEGER,

    @JsonProperty("picklistDouble")
    PICKLISTDOUBLE,

    @JsonProperty("picklistInteger")
    PICKLISTINTEGER,

    @JsonProperty("picklistString")
    PICKLISTSTRING,

    @JsonProperty("plainText")
    PLAINTEXT,

    @JsonProperty("string")
    STRING,

    @JsonProperty("treePath")
    TREEPATH;
}