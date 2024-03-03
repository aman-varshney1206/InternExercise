package org.ado.interfaces;

import org.ado.core.CoreApi;
import org.ado.workitemtracking.WorkItemTrackingApi;

public interface AdoClient {
     CoreApi getCoreApi();

     WorkItemTrackingApi getWorkItemTrackingApi();
}