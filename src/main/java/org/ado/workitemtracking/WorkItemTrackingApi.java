package org.ado.workitemtracking;

import org.ado.common.ApiVersion;
import org.ado.connection.Connection;
import org.ado.enums.*;
import org.ado.exceptions.AdoException;
import org.ado.helpers.JsonMapper;
import org.ado.helpers.URLHelper;
import org.ado.interfaces.WorkItemTrackingDetails;
import org.ado.workitemtracking.types.*;

import java.text.MessageFormat;
import java.util.*;
import java.util.Map.Entry;

import static org.ado.helpers.URLHelper.encodeSpace;
import static org.ado.utils.RestClient.send;

/***
 * WorkItem Tracking class to manage work items API
 */
public class WorkItemTrackingApi implements WorkItemTrackingDetails {

    /***
     * Connection object
     */
    private final Connection CONNECTION;
    private final JsonMapper MAPPER = new JsonMapper();
    private final String AREA = "wit";
    private final String WIT = "5264459e-e5e0-4bd8-b118-0985e68a4ec5";

    /***
     * Pass the connection object to work with WorkItem Tracking Api
     */
    public WorkItemTrackingApi(Connection connection) {
        this.CONNECTION = connection;
    }

    /***
     * Creates a single work item.
     */
    @Override
    public WorkItem createWorkItem(String workItemType,
                                   WorkItemOperation operation,
                                   String title) throws AdoException {
        var req = new HashMap<String, Object>() {{
            put("op", operation.toString().toLowerCase());
            put("path", "/fields/System.Title");
            put("from", null);
            put("value", title);
        }};

        String r = send(RequestMethod.POST, CONNECTION, WIT, CONNECTION.getProject(),
                AREA + "/workitems", null, "$" + encodeSpace(workItemType), ApiVersion.WORK_ITEM_TRACKING,
                null, List.of(req), CustomHeader.JSON_PATCH);

        return MAPPER.mapJsonResponse(r, WorkItem.class);
    }

    /***
     * Creates a single work item.
     */
    @Override
    public WorkItem createWorkItem(String workItemType,
                                   WorkItemOperation operation, String title,
                                   String description, String[] tags) throws AdoException {
        var t = new HashMap<String, Object>() {{
            put("op", operation.toString().toLowerCase());
            put("path", "/fields/System.Title");
            put("from", null);
            put("value", title);
        }};

        var d = new HashMap<String, Object>() {{
            put("op", operation.toString().toLowerCase());
            put("path", "/fields/System.Description");
            put("from", null);
            put("value", description);
        }};

        var tt = new HashMap<String, Object>() {{
            put("op", operation.toString().toLowerCase());
            put("path", "/fields/System.Tags");
            put("from", null);
            put("value", String.join(",", tags));
        }};

        var req = new ArrayList<>();
        req.add(t);
        req.add(d);
        req.add(tt);

        String r = send(RequestMethod.POST, CONNECTION, WIT, CONNECTION.getProject(),
                AREA + "/workitems", null, "$" + encodeSpace(workItemType), ApiVersion.WORK_ITEM_TRACKING,
                null, req, CustomHeader.JSON_PATCH);

        return MAPPER.mapJsonResponse(r, WorkItem.class);
    }

    /***
     * Creates a single work item optionally with additional fields.
     */
    @Override
    public WorkItem createWorkItem(String workItemType, String title, String description, Map<String, Object> additionalFields)
            throws AdoException {
        var req = new ArrayList<>();

        var t = new HashMap<String, Object>() {{
            put("op", "add");
            put("path", "/fields/System.Title");
            put("from", null);
            put("value", title);
        }};

        var d = new HashMap<String, Object>() {{
            put("op", "add");
            put("path", "/fields/System.Description");
            put("from", null);
            put("value", description);
        }};

        req.add(t);
        req.add(d);

        for (var key : additionalFields.keySet()) {
            var i = new HashMap<String, Object>() {{
                put("op", "add");
                put("path", "/fields/" + key);
                put("from", null);
                put("value", additionalFields.get(key));
            }};

            req.add(i);
        }

        String r = send(RequestMethod.POST, CONNECTION, WIT, CONNECTION.getProject(),
                AREA + "/workitems", null, "$" + encodeSpace(workItemType), ApiVersion.WORK_ITEM_TRACKING,
                null, req, CustomHeader.JSON_PATCH);

        return MAPPER.mapJsonResponse(r, WorkItem.class);
    }

    /***
     * Deletes the specified work item and sends it to the Recycle Bin, so that it can be restored back, if required.
     */
    @Override
    public WorkItemDelete deleteWorkItem(int id) throws AdoException {
        String r = send(RequestMethod.DELETE, CONNECTION, WIT, CONNECTION.getProject(),
                AREA + "/workitems", String.valueOf(id), null, ApiVersion.WORK_ITEM_TRACKING, null, null, null);

        return MAPPER.mapJsonResponse(r, WorkItemDelete.class);
    }

    /***
     * Deletes the specified work item permanently if the destroy parameter has been set to true,
     * WARNING: If the destroy parameter is set to true, work items deleted by this command will
     * NOT go to recycle-bin and there is no way to restore/recover them after deletion.
     */
    @Override
    public Void deleteWorkItem(int id, boolean destroy) throws AdoException {
        try {
            var q = new HashMap<String, Object>() {{
                put("destroy", destroy);
            }};

            String r = send(RequestMethod.DELETE, CONNECTION, WIT, CONNECTION.getProject(),
                    AREA + "/workitems", String.valueOf(id), null, ApiVersion.WORK_ITEM_TRACKING, q, null, null);

            if (!r.isEmpty()) MAPPER.mapJsonResponse(r, Map.class);
        } catch (AdoException e) {
            throw e;
        }
        return null;
    }

    /***
     * Returns a single work item.
     */
    @Override
    public WorkItem getWorkItem(int id) throws AdoException {
        String r = send(RequestMethod.GET, CONNECTION, WIT, CONNECTION.getProject(),
                AREA + "/workitems", Integer.toString(id), null, ApiVersion.WORK_ITEM_TRACKING, null, null, null);

        return MAPPER.mapJsonResponse(r, WorkItem.class);
    }

    /***
     * Returns a single work item.
     */
    @Override
    public WorkItem getWorkItem(int id, WorkItemExpand expand) throws AdoException {
        var q = new HashMap<String, Object>() {{
            put("$expand", expand.toString().toLowerCase());
        }};

        String r = send(RequestMethod.GET, CONNECTION, WIT, CONNECTION.getProject(),
                AREA + "/workitems", Integer.toString(id), null, ApiVersion.WORK_ITEM_TRACKING, q, null, null);

        return MAPPER.mapJsonResponse(r, WorkItem.class);
    }

    /***
     * Returns a single work item.
     */
    @Override
    public WorkItem getWorkItem(int id, WorkItemExpand expand, String asOf) throws AdoException {
        var q = new HashMap<String, Object>() {{
            put("asOf", asOf);
            put("$expand", expand.toString().toLowerCase());
        }};

        String r = send(RequestMethod.GET, CONNECTION, WIT, CONNECTION.getProject(),
                AREA + "/workitems", Integer.toString(id), null, ApiVersion.WORK_ITEM_TRACKING, q, null, null);

        return MAPPER.mapJsonResponse(r, WorkItem.class);
    }

    /***
     * Returns a single work item.
     */
    @Override
    public WorkItem getWorkItem(int id, WorkItemExpand expand, String[] fields) throws AdoException {
        var q = new HashMap<String, Object>() {{
            put("fields", String.join(",", fields));
            put("$expand", expand.toString().toLowerCase());
        }};

        String r = send(RequestMethod.GET, CONNECTION, WIT, CONNECTION.getProject(),
                AREA + "/workitems", Integer.toString(id), null, ApiVersion.WORK_ITEM_TRACKING, q, null, null);

        return MAPPER.mapJsonResponse(r, WorkItem.class);
    }

    /***
     * Returns a single work item.
     */
    @Override
    public WorkItem getWorkItem(int id, WorkItemExpand expand, String[] fields, String asOf) throws AdoException {
        var q = new HashMap<String, Object>() {{
            put("fields", String.join(",", fields));
            put("asOf", asOf);
            put("$expand", expand.toString().toLowerCase());
        }};

        String r = send(RequestMethod.GET, CONNECTION, WIT, CONNECTION.getProject(),
                AREA + "/workitems", Integer.toString(id), null, ApiVersion.WORK_ITEM_TRACKING, q, null, null);

        return MAPPER.mapJsonResponse(r, WorkItem.class);
    }

    /***
     * Returns a list of work items (Maximum 200).
     */
    @Override
    public WorkItemList getWorkItems(int[] ids) throws AdoException {
        var q = new HashMap<String, Object>() {{
            put("ids", intArrayToString(ids));
        }};

        String r = send(RequestMethod.GET, CONNECTION, WIT, CONNECTION.getProject(),
                AREA + "/workitems", null, null, ApiVersion.WORK_ITEM_TRACKING, q, null, null);

        return MAPPER.mapJsonResponse(r, WorkItemList.class);
    }

    /***
     * Returns a list of work items (Maximum 200)
     */
    @Override
    public WorkItemList getWorkItems(int[] ids, WorkItemExpand expand) throws AdoException {
        var q = new HashMap<String, Object>() {{
            put("ids", intArrayToString(ids));
            put("$expand", expand.toString().toLowerCase());
        }};

        String r = send(RequestMethod.GET, CONNECTION, WIT, CONNECTION.getProject(),
                AREA + "/workitems", null, null, ApiVersion.WORK_ITEM_TRACKING, q, null, null);

        return MAPPER.mapJsonResponse(r, WorkItemList.class);
    }

    /***
     * Returns a list of work items (Maximum 200)
     */
    @Override
    public WorkItemList getWorkItems(int[] ids, WorkItemExpand expand, String asOf) throws AdoException {
        var q = new HashMap<String, Object>() {{
            put("ids", intArrayToString(ids));
            put("$expand", expand.toString().toLowerCase());
            put("fields", asOf);
        }};

        String r = send(RequestMethod.GET, CONNECTION, WIT, CONNECTION.getProject(),
                AREA + "/workitems", null, null, ApiVersion.WORK_ITEM_TRACKING, q, null, null);

        return MAPPER.mapJsonResponse(r, WorkItemList.class);
    }

    /***
     * Returns a list of work items (Maximum 200)
     */
    @Override
    public WorkItemList getWorkItems(int[] ids, WorkItemExpand expand, String[] fields) throws AdoException {
        var q = new HashMap<String, Object>() {{
            put("ids", intArrayToString(ids));
            put("$expand", expand.toString().toLowerCase());
            put("fields", String.join(",", fields));
        }};

        String r = send(RequestMethod.GET, CONNECTION, WIT, CONNECTION.getProject(),
                AREA + "/workitems", null, null, ApiVersion.WORK_ITEM_TRACKING, q, null, null);

        return MAPPER.mapJsonResponse(r, WorkItemList.class);
    }

    /***
     * Returns a list of work items (Maximum 200)
     */
    @Override
    public WorkItemList getWorkItems(int[] ids, WorkItemExpand expand, String[] fields, String asOf, WorkItemErrorPolicy errorPolicy)
            throws AdoException {
        var q = new HashMap<String, Object>() {{
            put("ids", intArrayToString(ids));
            put("$expand", expand.toString().toLowerCase());
            put("asOf", asOf);
            put("fields", String.join(",", fields));
            put("errorPolicy", errorPolicy.toString().toLowerCase());
        }};

        String r = send(RequestMethod.GET, CONNECTION, WIT, CONNECTION.getProject(),
                AREA + "/workitems", null, null, ApiVersion.WORK_ITEM_TRACKING, q, null, null);

        return MAPPER.mapJsonResponse(r, WorkItemList.class);
    }

    /***
     * Returns the list of fully hydrated work item revisions.
     */
    @Override
    public WorkItemList getWorkItemRevisions(int workItemId) throws AdoException {
        String r = send(RequestMethod.GET, CONNECTION, WIT, CONNECTION.getProject(),
                AREA + "/workitems", Integer.toString(workItemId), "revisions", ApiVersion.WORK_ITEM_TRACKING, null, null, null);

        return MAPPER.mapJsonResponse(r, WorkItemList.class);
    }

    /***
     * Returns the list of fully hydrated work item revisions.
     */
    @Override
    public WorkItemList getWorkItemRevisions(int workItemId, WorkItemExpand expand) throws AdoException {
        var q = new HashMap<String, Object>() {{
            put("$expand", expand.toString().toLowerCase());
        }};

        String r = send(RequestMethod.GET, CONNECTION, WIT, CONNECTION.getProject(),
                AREA + "/workitems", Integer.toString(workItemId), "revisions", ApiVersion.WORK_ITEM_TRACKING, q, null, null);

        return MAPPER.mapJsonResponse(r, WorkItemList.class);
    }

    /***
     * Returns the list of fully hydrated work item revisions.
     */
    @Override
    public WorkItemRevisionList getWorkItemRevisions(WorkItemExpand expand, String startDateTime) throws AdoException {
        // only expand values - (fields or none)
        var q = new HashMap<String, Object>() {{
            put("$expand", expand.toString().toLowerCase());
            put("startDateTime", startDateTime);
        }};

        String r = send(RequestMethod.GET, CONNECTION, WIT, CONNECTION.getProject(),
                AREA + "/reporting", null, "workitemrevisions", "7.2-preview.2", q, null, null);

        return MAPPER.mapJsonResponse(r, WorkItemRevisionList.class);
    }

    /***
     *
     * Returns the list of fully hydrated work item revisions, paged.
     */
    @Override
    public WorkItemList getWorkItemRevisions(int workItemId, WorkItemExpand expand, int top, int skip) throws AdoException {
        var q = new HashMap<String, Object>() {{
            put("$expand", expand.toString().toLowerCase());
            put("$top", top);
            put("$skip", skip);
        }};

        String r = send(RequestMethod.GET, CONNECTION, WIT, CONNECTION.getProject(),
                AREA + "/workitems", Integer.toString(workItemId), "revisions", ApiVersion.WORK_ITEM_TRACKING, q, null, null);

        return MAPPER.mapJsonResponse(r, WorkItemList.class);
    }

    /***
     * Returns a fully hydrated work item for the requested revision
     */
    @Override
    public WorkItem getWorkItemRevision(int workItemId, int revisionNumber) throws AdoException {
        String r = send(RequestMethod.GET, CONNECTION, WIT, CONNECTION.getProject(),
                AREA + "/workitems", Integer.toString(workItemId), "revisions/" + revisionNumber,
                ApiVersion.WORK_ITEM_TRACKING, null, null, null);

        return MAPPER.mapJsonResponse(r, WorkItem.class);
    }

    /***
     * Returns a fully hydrated work item for the requested revision
     */
    @Override
    public WorkItem getWorkItemRevision(int workItemId, int revisionNumber, WorkItemExpand expand) throws AdoException {
        var q = new HashMap<String, Object>() {{
            put("$expand", expand.toString().toLowerCase());
        }};

        String r = send(RequestMethod.GET, CONNECTION, WIT, CONNECTION.getProject(),
                AREA + "/workitems", Integer.toString(workItemId), "revisions/" + revisionNumber,
                ApiVersion.WORK_ITEM_TRACKING, q, null, null);

        return MAPPER.mapJsonResponse(r, WorkItem.class);
    }

    /***
     * Update a single work item with the internal field names.
     */
    @Override
    public WorkItem updateWorkItem(int workItemId, Map<String, Object> fieldsToUpdate)
            throws AdoException {
        return updateWorkItem(workItemId, fieldsToUpdate, WorkItemOperation.ADD);
    }

    /***
     * Update a single work item with the internal field names.
     */
    @Override
    public WorkItem updateWorkItem(int workItemId, Map<String, Object> fieldsToUpdate, WorkItemOperation operation)
            throws AdoException {

        var req = new ArrayList<>();

        for (var key : fieldsToUpdate.keySet()) {
            var i = new HashMap<String, Object>() {{
                put("op", operation.name().toLowerCase());
                put("path", "/fields/" + key);
                put("from", null);
                if (operation != WorkItemOperation.REMOVE) {
                    put("value", fieldsToUpdate.get(key));
                }
            }};
            req.add(i);
        }

        String r = send(RequestMethod.PATCH, CONNECTION, WIT, CONNECTION.getProject(), AREA + "/workitems",
                Integer.toString(workItemId), null, ApiVersion.WORK_ITEM_TRACKING, null, req,
                CustomHeader.JSON_PATCH);

        return MAPPER.mapJsonResponse(r, WorkItem.class);
    }

    /***
     * Update a single work item with the internal field names.
     */
    @Override
    public WorkItem updateWorkItem(int workItemId, WorkItemExpand expand, boolean bypassRules,
                                   boolean suppressNotifications, boolean validateOnly, Map<String, Object> fieldsToUpdate)
            throws AdoException {

        return updateWorkItem(workItemId, expand, bypassRules, suppressNotifications, validateOnly, fieldsToUpdate,
                WorkItemOperation.ADD);
    }

    /***
     * Update a single work item with the internal field names.
     */
    @Override
    public WorkItem updateWorkItem(int workItemId, WorkItemExpand expand, boolean bypassRules,
                                   boolean suppressNotifications, boolean validateOnly, Map<String, Object> fieldsToUpdate,
                                   WorkItemOperation operation) throws AdoException {

        var req = new ArrayList<>();

        for (var key : fieldsToUpdate.keySet()) {
            var i = new HashMap<String, Object>() {{
                put("op", operation.name().toLowerCase());
                put("path", "/fields/" + key);
                put("from", null);
                if (operation != WorkItemOperation.REMOVE) {
                    put("value", fieldsToUpdate.get(key));
                }
            }};
            req.add(i);
        }

        var q = new HashMap<String, Object>() {{
            put("validateOnly", validateOnly);
            put("bypassRules", bypassRules);
            put("suppressNotifications", suppressNotifications);
            put("$expand", expand.toString().toLowerCase());
        }};

        String r = send(RequestMethod.PATCH, CONNECTION, WIT, CONNECTION.getProject(), AREA + "/workitems",
                Integer.toString(workItemId), null, ApiVersion.WORK_ITEM_TRACKING, q, req,
                CustomHeader.JSON_PATCH);

        return MAPPER.mapJsonResponse(r, WorkItem.class);
    }

    /**
     * Create hyperlinks for the given work item.
     */
    @Override
    public WorkItem addHyperLinks(int workItemId, Map<String, String> hyperlinksMap)
            throws AdoException {

        List<Object> reqBody = new ArrayList<>();

        for (Entry<String, String> hyperlinkEntry : hyperlinksMap.entrySet()) {
            String url = hyperlinkEntry.getKey();
            String comment = hyperlinkEntry.getValue();

            Map<String, Object> attributesMap = null;
            if (comment != null && !comment.isEmpty()) {
                attributesMap = new HashMap<>();
                attributesMap.put("comment", comment);
            }

            Map<String, Object> hyperlinkMap = new HashMap<>();
            hyperlinkMap.put("rel", "Hyperlink");
            hyperlinkMap.put("url", url);
            if (attributesMap != null) {
                hyperlinkMap.put("attributes", attributesMap);
            }

            Map<String, Object> reqBodyMap = new HashMap<>();
            reqBodyMap.put("op", WorkItemOperation.ADD.name().toLowerCase());
            reqBodyMap.put("path", "/relations/-");
            reqBodyMap.put("value", hyperlinkMap);

            reqBody.add(reqBodyMap);
        }

        String reply = send(RequestMethod.PATCH, CONNECTION, WIT, CONNECTION.getProject(), AREA + "/workitems",
                Integer.toString(workItemId), null, ApiVersion.WORK_ITEM_TRACKING, null, reqBody,
                CustomHeader.JSON_PATCH);

        return MAPPER.mapJsonResponse(reply, WorkItem.class);
    }

    /**
     * Remove hyperlinks for the given work item.
     */
    @Override
    public WorkItem removeHyperLinks(int workItemId, List<String> urls) throws AdoException {

        List<Object> reqBody = new ArrayList<>();

        List<WorkItemRelations> relations = getWorkItem(workItemId, WorkItemExpand.RELATIONS).getRelations();

        for (String url : urls) {
            int hyperlinkRelationNumber = -1;
            for (int i = 0; i < relations.size(); i++) {
                WorkItemRelations workItemRelations = relations.get(i);
                if (!workItemRelations.getRel().equals("Hyperlink")) {
                    continue;
                }
                if (workItemRelations.getUrl().equals(url)) {
                    hyperlinkRelationNumber = i;
                    break;
                }
            }

            if (hyperlinkRelationNumber == -1) {
                throw new AdoException(MessageFormat.format(
                        "Unable to remove hyperlink ''{0}'' from work item with ID ''{1}'': The hyperlink doesn't exist.",
                        url, hyperlinkRelationNumber));
            }

            Map<String, Object> reqBodyMap = new HashMap<>();
            reqBodyMap.put("op", WorkItemOperation.REMOVE.name().toLowerCase());
            reqBodyMap.put("path", "/relations/" + hyperlinkRelationNumber);

            reqBody.add(reqBodyMap);
        }

        String reply = send(RequestMethod.PATCH, CONNECTION, WIT, CONNECTION.getProject(), AREA + "/workitems",
                Integer.toString(workItemId), null, ApiVersion.WORK_ITEM_TRACKING, null, reqBody,
                CustomHeader.JSON_PATCH);

        return MAPPER.mapJsonResponse(reply, WorkItem.class);
    }

    /***
     * Returns the list of work item types
     */
    @Override
    public WorkItemTypes getWorkItemTypes() throws AdoException {
        String r = send(RequestMethod.GET, CONNECTION, WIT, CONNECTION.getProject(), AREA, null, "workitemtypes",
                ApiVersion.WORK_ITEM_TYPES, null, null, null);

        return MAPPER.mapJsonResponse(r, WorkItemTypes.class);
    }

    /***
     * Returns a work item type definition.
     */
    @Override
    public WorkItemType getWorkItemType(String workItemTypeName) throws AdoException {
        String r = send(RequestMethod.GET, CONNECTION, WIT, CONNECTION.getProject(), AREA, null,
                "workitemtypes/" + workItemTypeName, ApiVersion.WORK_ITEM_TYPES, null, null, null);

        return MAPPER.mapJsonResponse(r, WorkItemType.class);
    }

    /**
     * Returns information for all fields. The project ID/name parameter is optional.
     **/
    @Override
    public WorkItemFieldTypes getWorkItemFields() throws AdoException {
        String res = send(RequestMethod.GET, CONNECTION, null, null, AREA,
                null, "fields", ApiVersion.WORK_ITEM_TYPES, null, null, null);

        return MAPPER.mapJsonResponse(res, WorkItemFieldTypes.class);
    }

    /**
     * Returns information for all fields. The project ID/name parameter is optional.
     **/
    @Override
    public WorkItemFieldTypes getWorkItemFields(GetFieldsExpand expand) throws AdoException {
        var q = new HashMap<String, String>(){{ put("$expand", expand.name().toLowerCase()); }};

        String res = send(RequestMethod.GET, CONNECTION, null, null, AREA,
                null, "fields", ApiVersion.WORK_ITEM_TYPES, q, null, null);

        return MAPPER.mapJsonResponse(res, WorkItemFieldTypes.class);
    }

    /**
     * Gets information on a specific field.
     **/
    @Override
    public WorkItemField getWorkItemField(String fieldNameOrRefName) throws AdoException {
        String res = send(RequestMethod.GET, CONNECTION, null, null, AREA,
                null, "fields/" + URLHelper.encodeSpecialWithSpace(fieldNameOrRefName), ApiVersion.WORK_ITEM_TYPES, null, null, null);

        return MAPPER.mapJsonResponse(res, WorkItemField.class);
    }

    /**
     * Create a new field.
     **/
    @Override
    public WorkItemField createWorkItemField(WorkItemField workItemField) throws AdoException {
        String res = send(RequestMethod.POST, CONNECTION, null, null, AREA,
                null, "fields", ApiVersion.WORK_ITEM_TYPES, null, workItemField, CustomHeader.JSON_CONTENT_TYPE);

        return MAPPER.mapJsonResponse(res, WorkItemField.class);
    }

    /**
     * Deletes the field. To undelete a filed, see "Update Field" API.
     **/
    @Override
    public Void deleteWorkItemField(String fieldNameOrRefName) throws AdoException {
        try {
            String res = send(RequestMethod.DELETE, CONNECTION, null, null, AREA,
                    null, "fields/" + URLHelper.encodeSpecialWithSpace(fieldNameOrRefName), ApiVersion.WORK_ITEM_TYPES,
                    null, null, null);

            if (!res.isEmpty()) MAPPER.mapJsonResponse(res, Map.class);
        } catch (AdoException e) {
            throw e;
        }
        return null;
    }

    /**
     * Update a field.
     **/
    @Override
    public WorkItemField updateWorkItemField(String fieldNameOrRefName, boolean isDeleted) throws AdoException {
        var b = new UpdateWorkItemField(){{ setDeleted(isDeleted); }};

        String res = send(RequestMethod.PATCH, CONNECTION, null, null, AREA,
                null, "fields/" + URLHelper.encodeSpecialWithSpace(fieldNameOrRefName), ApiVersion.WORK_ITEM_TYPES, null, b, CustomHeader.JSON_CONTENT_TYPE);

        return MAPPER.mapJsonResponse(res, WorkItemField.class);
    }

    /***
     * Helper method to convert integer array to string.
     */
    private String intArrayToString(int[] i) {
        var r = Arrays.stream(i).mapToObj(String::valueOf).toArray(String[]::new);
        return String.join(",", r);
    }
}