package com.demo;

import com.example.ADOConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JacksonRead {
    private static final Logger LOGGER = LogManager.getLogger(JacksonRead.class);

    public static void main(String[] args) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ADOConfiguration adoConfiguration = objectMapper.readValue(new File(".\\data\\ADOConfig.json"), ADOConfiguration.class);

            // Accessing configuration data
            if (adoConfiguration != null) {
                LOGGER.info("System Name: {}", adoConfiguration.getSystemName());
                LOGGER.info("Username: {}", adoConfiguration.getUsername());
                LOGGER.info("API URL: {}", adoConfiguration.getApiUrl());

                // Accessing organisations
                List<ADOConfiguration.Organisation> organisations = adoConfiguration.getOrganisations();
                for (ADOConfiguration.Organisation organisation : organisations) {
                    LOGGER.info("Organisation: {}", organisation.getName());
                }

                // Accessing projects
                List<ADOConfiguration.Project> projects = adoConfiguration.getProjects();
                for (ADOConfiguration.Project project : projects) {
                    LOGGER.info("Project: {}", project.getName());
                    // Accessing teams, areas, and iterations similarly...
                }

                // Accessing fields
                // Access fields based on your specific requirements

                // Accessing mappings
                ADOConfiguration.Mappings mappings = adoConfiguration.getMappings();
                Map<String, String> workItemMappings = mappings.getWorkItems();
                for (Map.Entry<String, String> entry : workItemMappings.entrySet()) {
                    LOGGER.info("Work Item Mapping: {} -> {}", entry.getKey(), entry.getValue());
                }
            } else {
                LOGGER.info("Failed to create ADOConfiguration from JSON.");
            }

        } catch (Exception e) {
            LOGGER.error(e.getLocalizedMessage());
        }
    }
}

