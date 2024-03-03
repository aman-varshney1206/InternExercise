package com.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import com.example.ADOConfiguration.*;

@Log4j2
public class ApiCallScheduler {
    private final ADOConfiguration adoConfiguration;
    public ApiCallScheduler(ADOConfiguration adoConfig) {
        this.adoConfiguration = adoConfig;
    }

    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ADOConfiguration adoConfiguration = objectMapper.readValue(new File(".\\data\\ADOConfig.json"), ADOConfiguration.class);

        if(adoConfiguration == null) {
            log.error("Failed to create ADOConfiguration from JSON.");
        }
    }

    public void verifyJSONParseData() {
        log.info("System Name: {}", adoConfiguration.getSystemName());
        log.info("Username: {}", adoConfiguration.getUsername());
        log.info("API URL: {}", adoConfiguration.getApiUrl());

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
        String filePath = ".\\data\\file.txt";

        // Schedule a task to make a GET request every `timePeriod` seconds
        scheduler.scheduleAtFixedRate(() -> {
            try {
                // Read the last changed date from the file
                String lastUpdateTime = readChangedDateFromFile(filePath);
                System.out.println("Last update time: " + lastUpdateTime);

                // Parse the another date string
                Date inputDate = parseDateString(startDateTime);

                // Parse the last changed date from the file
                Date lastChangedDateParsed = parseDateString(lastUpdateTime);

                // Compare the dates
                int comparisonResult = compareDates(lastChangedDateParsed, inputDate);

                String startFetchingTime = startDateTime;

                // Output the comparison result
                if (comparisonResult < 0) {
                    System.out.println("The last updated time is earlier than the input date.");
                } else if (comparisonResult > 0) {
                    System.out.println("The last updated time is later than the input date.");
                    startFetchingTime = lastUpdateTime;
                } else {
                    System.out.println("The last updated time is equal to the input date.");
                }

                // fetch Revisions
                String response = getRevisions(startFetchingTime, adoApi);
                logAPIResponse(response, "Revisions");

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(response);

                // Extract values array
                JsonNode valuesArray = rootNode.get("values");

                if (valuesArray != null && valuesArray.isArray() && !valuesArray.isEmpty()) {
                    // Get the last element in the valuesArray
                    JsonNode lastValueNode = valuesArray.get(valuesArray.size() - 1);

                    // Extract ChangedDate from the last value
                    // String lastChangedDate = lastValueNode.at("/fields/System.ChangedDate").asText();

                    for (JsonNode valueNode : valuesArray) {
                        // Extract WorkItemType and Title from each value
                        String workItemType = valueNode.at("/fields/System.WorkItemType").asText();
                        String title = valueNode.at("/fields/System.Title").asText();

                        log.trace("Work Item Type: {}", workItemType);
                        log.trace("Work Item Title: {}", title);

                        // creating the workitem
                        adoApi.createWorkItem("amanvarshney0218",
                                "3de78c3c-a0dd-4e08-9c07-9120c585c40b", workItemType, title);

                        // Get the current date and time
                        String currentDateTime = getCurrentDateTime();
                        writeChangedDateToFile(currentDateTime, filePath);

                        TimeUnit.SECONDS.sleep(2);
                    }
                }

            } catch (Exception e) {
                log.error(e.getLocalizedMessage(), e);
            }
        }, 0, timePeriod, TimeUnit.SECONDS);

        //  shut down the scheduler
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.warn("Shutting down scheduler...");
            scheduler.shutdown();
        }));
    }

    private void writeChangedDateToFile(String date, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(date);
            System.out.println("ChangedDates written to file: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date currentDate = new Date();
        return dateFormat.format(currentDate);
    }

    private static String readChangedDateFromFile(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            return reader.readLine();
        }
    }

    private static Date parseDateString(String dateString) throws ParseException {
        SimpleDateFormat dateFormat;
        // Use one format for dates with time and another for dates without time
        if (dateString.contains("T")) {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        } else {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        }
        return dateFormat.parse(dateString);
    }

    private static int compareDates(Date date1, Date date2) {
        return date1.compareTo(date2);
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
            log.trace("{} response: {}", entity, getFormattedJSON(response));
        }else {
            log.error("Failed to get {} response", entity);
        }
    }

    public static String getFormattedJSON(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(json);

            // Convert JSON node to indented JSON string and return
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);

        } catch (Exception e) {
            log.error("Error formatting JSON: {}", e.getMessage());
        }

        return null;
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
