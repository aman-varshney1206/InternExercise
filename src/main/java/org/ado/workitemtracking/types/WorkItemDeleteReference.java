package org.ado.workitemtracking.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.ado.common.types.BaseAbstractMethod;

/***
 * Full deleted work item object.
 */
@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkItemDeleteReference extends BaseAbstractMethod {

    @JsonProperty("code")
    private int code;

    @JsonProperty("deletedBy")
    private String deletedBy;

    @JsonProperty("deletedDate")
    private String deletedDate;

    @JsonProperty("id")
    private int id;

    @JsonProperty("message")
    private String message;

    @JsonProperty("name")
    private String name;

    @JsonProperty("project")
    private String project;

    @JsonProperty("type")
    private String type;

    @JsonProperty("url")
    private String url;
}