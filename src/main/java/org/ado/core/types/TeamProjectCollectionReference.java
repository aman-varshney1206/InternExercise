package org.ado.core.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.ado.common.types.BaseAbstractMethod;

/***
 * Reference object for a TeamProjectCollection.
 */
@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamProjectCollectionReference extends BaseAbstractMethod {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("url")
    private String url;

}