package com.example;

import com.demo.Configuration;
import com.demo.Configuration.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.example.ADOConfiguration.*;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class DemoMain {

    public static void main(String[] args) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ADOConfiguration adoConfiguration = objectMapper.readValue(new File(".\\data\\ADOConfig.json"), ADOConfiguration.class);

            if(adoConfiguration == null) {
                log.error("Failed to create ADOConfiguration from JSON.");
            }else {
                ApiCallScheduler apiCallScheduler = new ApiCallScheduler(adoConfiguration);
                apiCallScheduler.verifyJSONParseData();

                ADOApi adoApi = new ADOApi(adoConfiguration);
                // Accessing organisations, projects
                List<Organisation> organisations = adoConfiguration.getOrganisations();
                List<Project> projects = adoConfiguration.getProjects();

                String organization = organisations.get(0).getName();
                String projectId = projects.get(1).getId();

                // String projectsResponse = adoApi.getProjects(organization);
                // String workItemResponse = adoApi.getWorkItem(organization, project, "10");

                // String pipelinesResponse = adoApi.getPipelines(organization, projectId);

                // logAPIResponse(ApiCallScheduler.getFormattedJSON(projectsResponse), "projects");
                // logAPIResponse(ApiCallScheduler.getFormattedJSON(workItemResponse), "workitem");

                // logAPIResponse(ApiCallScheduler.getFormattedJSON(pipelinesResponse), "pipelines");

//                String createGitRepoResponse = adoApi.createGitRepository(organization, projects.get(0), "radfjalsjd");
//                System.out.println(createGitRepoResponse);

                // String gitReposResponse = adoApi.getGitRepositories(organization, projectId);
                // logAPIResponse(ApiCallScheduler.getFormattedJSON(gitReposResponse), "git repos");



//                String createWorkItemResponse = adoApi.createWorkItem(organization, "sampl456", "Bug", "tile");
//                System.out.println(createWorkItemResponse);

//                List<String> repositoryIds = new ArrayList<>();
//                ObjectMapper objectMapper2 = new ObjectMapper();
//                JsonNode root = objectMapper2.readTree(gitReposResponse);

                // Creating a list of all repository ids
//                for (JsonNode repoNode : root.path("value")) {
//                    String repoId = repoNode.path("id").asText();
//                    repositoryIds.add(repoId);
//                }

//                 String createPipelineResponse = adoApi.createPipeline(organization, projectId, repositoryIds.get(0), "PipeCD");
//
//                 System.out.println(createPipelineResponse);



                // Prompt the user for the startDateTime input
//                Scanner scanner = new Scanner(System.in);
//                System.out.print("\n\nEnter startDateTime (e.g., 2024-02-01T01:45:30 or 2024-02-01): ");
//                String startDateTime = scanner.nextLine();
//
//                apiCallScheduler.scheduleAPIRequests(startDateTime, 15, adoApi);

            }

        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }

    private static void logAPIResponse(String response, String entity) {
        if(response != null) {
            log.trace("{} response: {}", entity, response);
        }else {
            log.error("Failed to get {} response", entity);
        }
    }

    private static Object createAddOperation(String path, String value) {
        return Map.of("op", "add", "path", path, "from", null, "value", value);
    }
}
