package org.ado.core.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.ado.common.types.BaseAbstractMethod;

/***
 * Represents process
 */
@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Process extends BaseAbstractMethod {

    @JsonProperty("id")
    private String id;

    @JsonProperty("description")
    private String description;

    @JsonProperty("isDefault")
    private boolean isDefault;

    @JsonProperty("type")
    private String type;

    @JsonProperty("url")
    private String url;

    @JsonProperty("name")
    private String name;

}