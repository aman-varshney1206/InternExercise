package org.ado.interfaces;

import org.ado.enums.*;
import org.ado.exceptions.AdoException;
import org.ado.workitemtracking.types.*;

import java.util.List;
import java.util.Map;

public interface WorkItemTrackingDetails {
    WorkItem createWorkItem(String workItemType, WorkItemOperation operation, String title)
            throws AdoException;

    WorkItem createWorkItem(String workItemType, WorkItemOperation operation, String title, String description,
                            String[] tags) throws AdoException;

    WorkItem createWorkItem(String workItemType, String title, String description,
                            Map<String, Object> additionalFields) throws AdoException;

    WorkItemDelete deleteWorkItem(int id) throws AdoException;

    Void deleteWorkItem(int id, boolean destroy) throws AdoException;

    WorkItem getWorkItem(int id) throws AdoException;

    WorkItem getWorkItem(int id, WorkItemExpand expand) throws AdoException;

    WorkItem getWorkItem(int id, WorkItemExpand expand, String asOf) throws AdoException;

    WorkItem getWorkItem(int id, WorkItemExpand expand, String[] fields) throws AdoException;

    WorkItem getWorkItem(int id, WorkItemExpand expand, String[] fields, String asOf)
            throws AdoException;

    WorkItemList getWorkItems(int[] ids) throws AdoException;

    WorkItemList getWorkItems(int[] ids, WorkItemExpand expand) throws AdoException;

    WorkItemList getWorkItems(int[] ids, WorkItemExpand expand, String asOf) throws AdoException;

    WorkItemList getWorkItems(int[] ids, WorkItemExpand expand, String[] fields)
            throws AdoException;

    WorkItemList getWorkItems(int[] ids, WorkItemExpand expand, String[] fields, String asOf,
                              WorkItemErrorPolicy errorPolicy) throws AdoException;

    WorkItemList getWorkItemRevisions(int workItemId) throws AdoException;

    WorkItemList getWorkItemRevisions(int workItemId, WorkItemExpand expand) throws AdoException;

    WorkItemRevisionList getWorkItemRevisions(WorkItemExpand expand, String startDateTime) throws AdoException;

    WorkItemList getWorkItemRevisions(int workItemId, WorkItemExpand expand, int top, int skip)
            throws AdoException;

    WorkItem getWorkItemRevision(int workItemId, int revisionNumber) throws AdoException;

    WorkItem getWorkItemRevision(int workItemId, int revisionNumber, WorkItemExpand expand)
            throws AdoException;

    WorkItem updateWorkItem(int workItemId, Map<String, Object> fieldsToUpdate)
            throws AdoException;

    WorkItem updateWorkItem(int workItemId, Map<String, Object> fieldsToUpdate, WorkItemOperation operation)
            throws AdoException;

    WorkItem updateWorkItem(int workItemId, WorkItemExpand expand, boolean bypassRules, boolean suppressNotifications,
                            boolean validateOnly, Map<String, Object> fieldsToUpdate) throws AdoException;

    WorkItem updateWorkItem(int workItemId, WorkItemExpand expand, boolean bypassRules, boolean suppressNotifications,
                            boolean validateOnly, Map<String, Object> fieldsToUpdate, WorkItemOperation operation) throws AdoException;

    WorkItem addHyperLinks(int workItemId, Map<String, String> hyperlinksMap) throws AdoException;

    WorkItem removeHyperLinks(int workItemId, List<String> urls) throws AdoException;

    WorkItemTypes getWorkItemTypes() throws AdoException;

    WorkItemType getWorkItemType(String workItemTypeName) throws AdoException;

    WorkItemFieldTypes getWorkItemFields() throws AdoException;

    WorkItemFieldTypes getWorkItemFields(GetFieldsExpand expand) throws AdoException;

    WorkItemField getWorkItemField(String fieldNameOrRefName) throws AdoException;

    WorkItemField createWorkItemField(WorkItemField workItemField) throws AdoException;

    Void deleteWorkItemField(String fieldNameOrRefName) throws AdoException;

    WorkItemField updateWorkItemField(String fieldNameOrRefName, boolean isDeleted) throws AdoException;

}