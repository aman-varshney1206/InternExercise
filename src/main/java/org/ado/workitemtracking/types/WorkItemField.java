package org.ado.workitemtracking.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.ado.common.types.BaseAbstractMethod;
import org.ado.enums.FieldType;
import org.ado.enums.FieldUsage;

import java.util.List;

/**
 * Describes a field on a work item and it's properties specific to that work item type.
 **/
@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkItemField extends BaseAbstractMethod {

    @JsonProperty("_links")
    private Object _links;

    @JsonProperty("canSortBy")
    private boolean canSortBy;

    @JsonProperty("description")
    private String description;

    @JsonProperty("isDeleted")
    private boolean isDeleted;

    @JsonProperty("isIdentity")
    private boolean isIdentity;

    @JsonProperty("isPicklist")
    private boolean isPicklist;

    @JsonProperty("isPicklistSuggested")
    private boolean isPicklistSuggested;

    @JsonProperty("isQueryable")
    private boolean isQueryable;

    @JsonProperty("name")
    private String name;

    @JsonProperty("picklistId")
    private String picklistId;

    @JsonProperty("readOnly")
    private boolean readOnly;

    @JsonProperty("referenceName")
    private String referenceName;

    @JsonProperty("supportedOperations")
    private List<WorkItemFieldOperation> supportedOperations;

    @JsonProperty("type")
    private FieldType type;

    @JsonProperty("url")
    private String url;

    @JsonProperty("usage")
    private FieldUsage usage;

}