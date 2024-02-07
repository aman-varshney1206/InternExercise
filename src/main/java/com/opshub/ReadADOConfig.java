package com.opshub;

import com.other.Organisation;
import com.other.Project;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ReadADOConfig {
    public static void main(String[] args) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonConfig = (JSONObject) parser.parse(new FileReader(".\\data\\ADOConfig.json"));

            ADOConfiguration adoConfiguration = createADOConfigurationFromJson(jsonConfig);

            // Verify and print data
            if (adoConfiguration != null) {
                System.out.println("Data read from JSON file is correct:");
                System.out.println("System Name: " + adoConfiguration.getSystemName());
                System.out.println("Username: " + adoConfiguration.getUsername());
                System.out.println("Password: " + adoConfiguration.getPassword());
                System.out.println("API URL: " + adoConfiguration.getApiUrl());

                System.out.println("Organisations:");
                for (Organisation organisation : adoConfiguration.getOrganisations()) {
                    System.out.println("  - " + organisation.getName());
                }

                System.out.println("Projects:");
                for (Project project : adoConfiguration.getProjects()) {
                    System.out.println("  - Project Name: " + project.getName());
                    // Print teams, areas, and iterations inside each project
                }
            } else {
                System.out.println("Failed to create ADOConfiguration from JSON.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ADOConfiguration createADOConfigurationFromJson(JSONObject jsonConfig) {
        try {
            // Assuming you have implemented the constructors and setters for ADOConfiguration and related classes
            ADOConfiguration adoConfiguration = new ADOConfiguration();
            adoConfiguration.setSystemName((String) jsonConfig.get("system_name"));
            adoConfiguration.setUsername((String) jsonConfig.get("username"));
            adoConfiguration.setPassword((String) jsonConfig.get("password"));
            adoConfiguration.setApiUrl((String) jsonConfig.get("apiUrl"));

            // Set organisations
            List<Organisation> organisations = new ArrayList<>();
            JSONArray organisationsArray = (JSONArray) jsonConfig.get("organisations");
            for (Object orgObject : organisationsArray) {
                JSONObject orgJson = (JSONObject) orgObject;
                Organisation organisation = new Organisation();
                organisation.setName((String) orgJson.get("name"));
                organisations.add(organisation);
            }
            adoConfiguration.setOrganisations(organisations);

            // Set projects
            List<Project> projects = new ArrayList<>();
            JSONArray projectsArray = (JSONArray) jsonConfig.get("projects");
            for (Object projectObject : projectsArray) {
                JSONObject projectJson = (JSONObject) projectObject;
                Project project = new Project();
                project.setId((String) projectJson.get("_id"));
                project.setName((String) projectJson.get("name"));

                // Set teams, areas, and iterations similarly

                projects.add(project);
            }
            adoConfiguration.setProjects(projects);

            return adoConfiguration;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
