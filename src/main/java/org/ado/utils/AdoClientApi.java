package org.ado.utils;

import org.ado.connection.Connection;
import org.ado.core.CoreApi;
import org.ado.interfaces.AdoClient;
import org.ado.workitemtracking.WorkItemTrackingApi;

/***
 * AdoClientApi class to easily call VSTS REST Api with the connection parameters
 */
public class AdoClientApi implements AdoClient {

    private final Connection CONNECTION;

    public AdoClientApi(Connection connection) {
        this.CONNECTION = connection;
    }

    /***
     * Pass the VSTS organization name and personal access token to create a connection object
     */
    public AdoClientApi(String organizationName, String personalAccessToken) {
        this.CONNECTION = new Connection(organizationName, personalAccessToken);
    }

    /***
     * Pass the VSTS organization name, project name and personal access token to create a connection object
     */
    public AdoClientApi(String organizationName, String projectName, String personalAccessToken) {
        this.CONNECTION = new Connection(organizationName, projectName, personalAccessToken);
    }

    public void setProject(String project) {
        if (this.CONNECTION != null) {
            this.CONNECTION.setProject(project);
        }
    }

    public String getOrganization() {
        if (this.CONNECTION != null) {
            return this.CONNECTION.getOrganization();
        }
        return null;
    }

    public void setOrganization(String org) {
        if (this.CONNECTION != null) {
            this.CONNECTION.setOrganization(org);
        }
    }

    public Connection getConnection() {
        return this.CONNECTION;
    }


    /***
     * Returns an instance of Core Api
     */
    @Override
    public CoreApi getCoreApi() {
        return new CoreApi(CONNECTION);
    }

    /***
     * Returns an instance of Work item tracking Api
     */
    @Override
    public WorkItemTrackingApi getWorkItemTrackingApi() {
        return new WorkItemTrackingApi(CONNECTION);
    }
}