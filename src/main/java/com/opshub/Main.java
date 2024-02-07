package com.opshub;

import com.other.Organisation;
import com.other.Project;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class Main {

    public static void main(String[] args) {

        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonConfig = (JSONObject) parser.parse(new FileReader(".\\data\\ADOConfig.json"));
            ADOConfiguration adoConfiguration = ReadADOConfig.createADOConfigurationFromJson(jsonConfig);

            // Verify and print data
            if (adoConfiguration != null) {

                System.out.println("Data read from JSON file is correct:");
                System.out.println("System Name: " + adoConfiguration.getSystemName());
                System.out.println("Username: " + adoConfiguration.getUsername());
                System.out.println("API URL: " + adoConfiguration.getApiUrl());

                // Print organisations
                System.out.println("Organisations:");
                for (Organisation organisation : adoConfiguration.getOrganisations()) {
                    System.out.println("  - " + organisation.getName());
                }

                // Print projects
                System.out.println("Projects:");
                for (Project project : adoConfiguration.getProjects()) {
                    System.out.println("  - Project Name: " + project.getName());
                    // Print teams, areas, and iterations inside each project...
                }

                // Use ADOApiService
                ADOApiService adoApiService = new ADOApiService(adoConfiguration);

                String organization = adoConfiguration.getOrganisations().get(0).getName();
                String project = String.valueOf(adoConfiguration.getProjects().get(1).getName());

                String pipelinesResponse = adoApiService.getPipelines(organization, project);
                String projectsResponse = adoApiService.getProjects(organization);
                String workItemResponse = adoApiService.getWorkItem(organization, project, "10");
                String gitReposResponse = adoApiService.getGitRepositories(organization, project);

                if (projectsResponse != null) {
                    System.out.println("Project Response:");
                    // LOGGER.debug(projectsResponse);
                    System.out.println(projectsResponse);
                } else {
                    System.out.println("Failed to get projects.");
                }

                if(pipelinesResponse != null) {
                    System.out.println("Pipeline response: ");
                    System.out.println(pipelinesResponse);
                    // LOGGER.debug(pipelinesResponse);
                } else {
                    System.out.println("Failed to get pipelines");
                }

                if(workItemResponse != null) {
                    System.out.println("WorkItem response: ");
                    System.out.println(workItemResponse);
                    // LOGGER.debug(pipelinesResponse);
                } else {
                    System.out.println("Failed to get workitem");
                }

                if(gitReposResponse != null) {
                    System.out.println("Git Repo response: ");
                    System.out.println(gitReposResponse);
                    // LOGGER.debug(pipelinesResponse);
                } else {
                    System.out.println("Failed to get git repos");
                }
            } else {
                System.out.println("Failed to create ADOConfiguration from JSON.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
