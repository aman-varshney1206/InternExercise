package org.ado.workitemtracking.types;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.ado.common.types.BaseAbstractMethod;

/***
 * Collection of link attributes.
 */
@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkItemAttributes extends BaseAbstractMethod {

    @JsonProperty("isLocked")
    private boolean isLocked;

    @JsonProperty("comment")
    private String comment;

    @JsonProperty("name")
    private String name;

    @JsonProperty("authorizedDate")
    private String authorizedDate;

    @JsonProperty("id")
    private int id;

    @JsonProperty("resourceCreatedDate")
    private String resourceCreatedDate;

    @JsonProperty("revisedDate")
    private String revisedDate;
    @JsonProperty("resourceModifiedDate")
    private String resourceModifiedDate;


}