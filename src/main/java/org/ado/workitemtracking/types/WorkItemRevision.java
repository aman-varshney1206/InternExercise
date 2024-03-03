package org.ado.workitemtracking.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.ado.common.types.BaseAbstractMethod;

/***
 * Describes a work item revision.
 */

@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkItemRevision extends BaseAbstractMethod {
    @JsonProperty("id")
    private int id;

    @JsonProperty("rev")
    private int rev;

    @JsonProperty("fields")
    private WorkItemRevisionFields fields;
}
