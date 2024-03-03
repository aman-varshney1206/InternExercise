package org.ado.workitemtracking.types;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.ado.common.types.Author;
import org.ado.common.types.BaseAbstractMethod;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkItemRevisionFields extends BaseAbstractMethod {
    @JsonProperty("System.Id")
    private int systemId;
    @JsonProperty("System.AreaId")
    private int systemAreaId;
    @JsonProperty("System.AreaPath")
    private String systemAreaPath;
    @JsonProperty("System.TeamProject")
    private String systemTeamProject;
    @JsonProperty("System.NodeName")
    private String systemNodeName;
    @JsonProperty("System.AreaLevel1")
    private String systemAreaLevel1;
    @JsonProperty("System.Rev")
    private int systemRev;
    @JsonProperty("System.AuthorizedDate")
    private String systemAuthorizedDate;
    @JsonProperty("System.RevisedDate")
    private String systemRevisedDate;
    @JsonProperty("System.IterationId")
    private int systemIterationId;
    @JsonProperty("System.IterationPath")
    private String systemIterationPath;
    @JsonProperty("System.IterationLevel1")
    private String systemIterationLevel1;
    @JsonProperty("System.WorkItemType")
    private String systemWorkItemType;
    @JsonProperty("System.State")
    private String systemState;
    @JsonProperty("System.Reason")
    private String systemReason;
    @JsonProperty("System.AssignedTo")
    private String systemAssignedTo;
    @JsonProperty("System.CreatedDate")
    private String SystemCreatedDate;
    @JsonProperty("System.CreatedBy")
    private String systemCreatedBy;
    @JsonProperty("System.ChangedDate")
    private String systemChangedDate;
    @JsonProperty("System.ChangedBy")
    private String systemChangedBy;
    @JsonProperty("System.AuthorizedAs")
    private String systemAuthorizedAs;
    @JsonProperty("System.PersonId")
    private int systemPersonId;
    @JsonProperty("System.Watermark")
    private int systemWatermark;
    @JsonProperty("System.CommentCount")
    private int systemCommentCount;
    @JsonProperty("System.Title")
    private String systemTitle;
    @JsonProperty("System.BoardColumn")
    private String systemBoardColumn;
    @JsonProperty("System.BoardColumnDone")
    private boolean systemBoardColumnDone;
    @JsonProperty("Microsoft.VSTS.Scheduling.StoryPoints")
    private double storyPoints;
    @JsonProperty("Microsoft.VSTS.Common.StateChangeDate")
    private String stateChangeDate;
    @JsonProperty("Microsoft.VSTS.Common.ActivatedDate")
    private String activatedDate;
    @JsonProperty("Microsoft.VSTS.Common.ActivatedBy")
    private String activatedBy;
    @JsonProperty("Microsoft.VSTS.Common.Priority")
    private int priority;
    @JsonProperty("Microsoft.VSTS.Common.Risk")
    private String risk;
    @JsonProperty("System.Description")
    private String systemDescription;
    @JsonProperty("Microsoft.VSTS.Common.AcceptanceCriteria")
    private String acceptanceCriteria;
    @JsonProperty("System.Tags")
    private String systemTags;
}
