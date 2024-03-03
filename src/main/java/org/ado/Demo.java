package org.ado;

import org.ado.core.types.Project;
import org.ado.enums.WorkItemExpand;
import org.ado.exceptions.AdoException;
import org.ado.utils.AdoClientApi;

import org.ado.workitemtracking.types.WorkItemRevision;
import org.ado.workitemtracking.types.WorkItemRevisionFields;


import java.sql.SQLException;
import java.util.List;

public class Demo {
    public static void main(String[] args) {
        String organisation = "amanvarshney0218";
        String project = "ABC";
        String personalAccessToken = "hwligrtzaavewae2onkiwl6nkkpcr7fgv3gzwrd6ako5bzyowwgq";





        // Connect Azure DevOps API with organisation name and personal access token.
        var webApi = new AdoClientApi(organisation, personalAccessToken);

        var core = webApi.getCoreApi();
        var workItemTracking = webApi.getWorkItemTrackingApi();

        try {


            Project p1 = core.getProject(project);
            List<WorkItemRevision> w = workItemTracking
                    .getWorkItemRevisions(WorkItemExpand.FIELDS, "2024-02-24")
                    .getWorkItemRevisions();
            System.out.println(p1.getName());
            System.out.println(p1.getDescription());
            for(WorkItemRevision wi: w) {
                System.out.println(wi.getId() + " " + wi.getRev());
                WorkItemRevisionFields wif = wi.getFields();
                System.out.println(wif.getSystemTitle());
            }

        } catch (AdoException e) {
            e.printStackTrace();
        }
    }
}
