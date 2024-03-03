package org.ado.core.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.ado.common.types.BaseAbstractMethod;

import java.util.List;

/***
 * List of project
 */

@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Projects extends BaseAbstractMethod {

    @JsonProperty("value")
    private List<Project> projects;

}