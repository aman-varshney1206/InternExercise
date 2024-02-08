package com.example;

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
            ADOConfiguration2 adoConfiguration = objectMapper.readValue(new File(".\\data\\ADOConfig.json"), ADOConfiguration2.class);

            // Accessing configuration data
            if (adoConfiguration != null) {
                LOGGER.info("System Name: {}", adoConfiguration.getSystemName());
                LOGGER.info("Username: {}", adoConfiguration.getUsername());
                LOGGER.info("API URL: {}", adoConfiguration.getApiUrl());

                // Accessing organisations
                List<ADOConfiguration2.Organisation> organisations = adoConfiguration.getOrganisations();
                for (ADOConfiguration2.Organisation organisation : organisations) {
                    LOGGER.info("Organisation: {}", organisation.getName());
                }

                // Accessing projects
                List<ADOConfiguration2.Project> projects = adoConfiguration.getProjects();
                for (ADOConfiguration2.Project project : projects) {
                    LOGGER.info("Project: {}", project.getName());
                    // Accessing teams, areas, and iterations similarly...
                }

                // Accessing fields
                // Access fields based on your specific requirements

                // Accessing mappings
                ADOConfiguration2.Mappings mappings = adoConfiguration.getMappings();
                Map<String, String> workItemMappings = mappings.getWorkItems();
                for (Map.Entry<String, String> entry : workItemMappings.entrySet()) {
                    LOGGER.info("Work Item Mapping: {} -> {}", entry.getKey(), entry.getValue());
                }
            } else {
                LOGGER.info("Failed to create ADOConfiguration2 from JSON.");
            }

        } catch (Exception e) {
            LOGGER.error(e.getLocalizedMessage());
        }
    }
}

