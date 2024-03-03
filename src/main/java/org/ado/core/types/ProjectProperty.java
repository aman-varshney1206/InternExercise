package org.ado.core.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.ado.common.types.BaseAbstractMethod;

/***
 * A named value associated with a project.
 */

@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectProperty extends BaseAbstractMethod {

    @JsonProperty("name")
    private String name;

    @JsonProperty("value")
    private Object value;

}