package com.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.example.ADOConfiguration.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ADOConfiguration adoConfiguration = objectMapper.readValue(new File(".\\data\\ADOConfig.json"), ADOConfiguration.class);

            if(adoConfiguration == null) {
                LOGGER.error("Failed to create ADOConfiguration from JSON.");
            }else {
                ApiCallScheduler apiCallScheduler = new ApiCallScheduler(adoConfiguration);
                apiCallScheduler.verifyJSONParseData();

                ADOApi adoApi = new ADOApi(adoConfiguration);
                // Accessing organisations, projects
                List<Organisation> organisations = adoConfiguration.getOrganisations();
                List<Project> projects = adoConfiguration.getProjects();

                String organization = organisations.get(0).getName();
                String project = projects.get(0).getName();

                String projectsResponse = adoApi.getProjects(organization);
                String workItemResponse = adoApi.getWorkItem(organization, project, "10");
                String gitReposResponse = adoApi.getGitRepositories(organization, project);
                String pipelinesResponse = adoApi.getPipelines(organization, project);

                logAPIResponse(ApiCallScheduler.getFormattedJSON(projectsResponse), "projects");
                logAPIResponse(ApiCallScheduler.getFormattedJSON(workItemResponse), "workitem");
                logAPIResponse(ApiCallScheduler.getFormattedJSON(gitReposResponse), "git repos");
                logAPIResponse(ApiCallScheduler.getFormattedJSON(pipelinesResponse), "pipelines");

                List<String> repositoryIds = new ArrayList<>();
                ObjectMapper objectMapper2 = new ObjectMapper();
                JsonNode root = objectMapper2.readTree(gitReposResponse);

                // Creating a list of all repository ids
                for (JsonNode repoNode : root.path("value")) {
                    String repoId = repoNode.path("id").asText();
                    repositoryIds.add(repoId);
                }

                // Prompt the user for the startDateTime input
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter startDateTime (e.g., 2024-02-01T01:45:30 or 2024-02-01): ");
                String startDateTime = scanner.nextLine();

                apiCallScheduler.scheduleAPIRequests(startDateTime, 15, adoApi);

            }

        } catch (Exception e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }
    }

    private static void logAPIResponse(String response, String entity) {
        if(response != null) {
            LOGGER.info("{} response: {}", entity, response);
        }else {
            LOGGER.error("Failed to get {} response", entity);
        }
    }
}
