package org.ado.workitemtracking.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.ado.common.types.BaseAbstractMethod;

import java.util.List;

/***
 * Describes a work item.
 */
@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkItem extends BaseAbstractMethod {

    @JsonProperty("id")
    private int id;

    @JsonProperty("rev")
    private int rev;

    @JsonProperty("fields")
    private WorkItemFields fields;

    @JsonProperty("relations")
    private List<WorkItemRelations> relations;

    @JsonProperty("_links")
    private Object _links;

    @JsonProperty("url")
    private String url;

}