package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.example.ADOConfiguration.*;

public class ApiCallScheduler {
    private static final Logger LOGGER = LogManager.getLogger(ApiCallScheduler.class);

    private ADOConfiguration adoConfiguration;

    public ApiCallScheduler(ADOConfiguration adoConfig) {
        this.adoConfiguration = adoConfig;
    }

    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ADOConfiguration adoConfiguration = objectMapper.readValue(new File(".\\data\\ADOConfig.json"), ADOConfiguration.class);

        if(adoConfiguration == null) {
            LOGGER.error("Failed to create ADOConfiguration from JSON.");
        }
    }

    public void verifyJSONParseData() {
        LOGGER.info("System Name: {}", adoConfiguration.getSystemName());
        LOGGER.info("Username: {}", adoConfiguration.getUsername());
        LOGGER.info("API URL: {}", adoConfiguration.getApiUrl());

        // Accessing organisations, projects
        List<Organisation> organisations = adoConfiguration.getOrganisations();
        for(Organisation org: organisations) {
            System.out.println(org.getName());
        }

        List<Project> projects = adoConfiguration.getProjects();
        for(Project proj: projects) {
            System.out.println(proj.getName());
        }
    }

    public void scheduleAPIRequests(String startDateTime, int timePeriod, ADOApi adoApi) {
        // Create a scheduled executor service with a single-threaded executor
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        // Schedule a task to make a GET request every 5 seconds
        scheduler.scheduleAtFixedRate(() -> {
            try {
                // fetch Revisions
                String response = getRevisions(startDateTime, adoApi);
                logAPIResponse(response, "Revisions");

                // Process the API response

            } catch (Exception e) {
                LOGGER.error(e.getLocalizedMessage(), e);
            }
        }, 0, timePeriod, TimeUnit.SECONDS);

        //  shut down the scheduler
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LOGGER.warn("Shutting down scheduler...");
            scheduler.shutdown();
        }));
    }


    private String getRevisions(String startDateTime, ADOApi adoApi) {
        List<Organisation> organisations = adoConfiguration.getOrganisations();
        List<Project> projects = adoConfiguration.getProjects();

        String organization = organisations.get(0).getName();
        String project = projects.get(0).getName();

        return adoApi.getWorkItemRevisions(organization, project, startDateTime);
    }

    private static void logAPIResponse(String response, String entity) {
        if(response != null) {
            LOGGER.info("{} response: {}", entity, response);
        }else {
            LOGGER.error("Failed to get {} response", entity);
        }
    }

    // Function to make a GET request and return the response as a String
    private static String makeGetRequest(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        } finally {
            connection.disconnect();
        }
    }
}
