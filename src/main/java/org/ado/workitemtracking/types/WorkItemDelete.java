package org.ado.workitemtracking.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.ado.common.types.BaseAbstractMethod;

/***
 * Full deleted work item object
 */
@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkItemDelete extends BaseAbstractMethod {

    @JsonProperty("id")
    private int id;

    @JsonProperty("type")
    private String type;

    @JsonProperty("name")
    private String name;

    @JsonProperty("project")
    private String project;

    @JsonProperty("resource")
    private WorkItem resource;

    @JsonProperty("deletedDate")
    private String deletedDate;

    @JsonProperty("deletedBy")
    private String deletedBy;

    @JsonProperty("code")
    private int code;

    @JsonProperty("url")
    private String url;

}