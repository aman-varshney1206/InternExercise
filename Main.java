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

                if (projectsResponse != null) {
                    System.out.println("Project Response:");
                    System.out.println(projectsResponse);
                } else {
                    System.out.println("Failed to get projects.");
                }

                if(pipelinesResponse != null) {
                    System.out.println("Pipeline response: ");
                    System.out.println(pipelinesResponse);
                } else {
                    System.out.println("Failed to get pipelines");
                }
            } else {
                System.out.println("Failed to create ADOConfiguration from JSON.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
