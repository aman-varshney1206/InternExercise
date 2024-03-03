package org.ado;

import com.example.ADOApi;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.ado.helpers.DateTimeHelper.*;


@Log4j2
public class ScheduleApiCall {

    private final Configuration configuration;

    public ScheduleApiCall(Configuration config) {this.configuration = config;}

    public void scheduleAPIRequests(String startDateTime, int timePeriod) {
        // Create a scheduled executor service with a single-threaded executor
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        String filePath = ".\\data\\file.txt";

        // Schedule a task to make a GET request every `timePeriod` seconds
        scheduler.scheduleAtFixedRate(() -> {
            try {
                // Read the last changed date from the file
                String lastUpdateTime = readDateFromFile(filePath);
               log.trace("Last update time: {}", lastUpdateTime);

                // Parse the another date string
                Date inputDate = parseDateString(startDateTime);

                // Parse the last changed date from the file
                Date lastChangedDateParsed = parseDateString(lastUpdateTime);

                // Compare the dates
                int comparisonResult = compareDates(lastChangedDateParsed, inputDate);

                String startFetchingTime = startDateTime;

                // Output the comparison result
                if (comparisonResult < 0) {
                    log.trace("The last updated time is earlier than the input date.");
                } else if (comparisonResult > 0) {
                    log.trace("The last updated time is later than the input date.");
                    startFetchingTime = lastUpdateTime;
                } else {
                    log.trace("The last updated time is equal to the input date.");
                }

                // fetch Revisions
                // api call to get revisions
                String response = getRevisions(startFetchingTime);
                logAPIResponse(response, "Revisions");

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(response);

                // Extract values array
                JsonNode valuesArray = rootNode.get("values");

                // iterate all revisions
                for (JsonNode valueNode : valuesArray) {
                    // Extract WorkItemType and Title from each value
                    String workItemType = valueNode.at("/fields/System.WorkItemType").asText();
                    String title = valueNode.at("/fields/System.Title").asText();

                    log.trace("Work Item Type: {}", workItemType);
                    log.trace("Work Item Title: {}", title);

                    // creating the workitem
                    createInTarget(workItemType, title);

                    TimeUnit.SECONDS.sleep(2);
                }
                // Get the current date and time
                String currentDateTime = getCurrentDateTime();
                writeDateToFile(currentDateTime, filePath);

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


    private String getRevisions(String startDateTime) {
        String sourceOrg = this.configuration.getSource().getOrganisation();
        String sourceProj = this.configuration.getSource().getProject().getName();
        String username = this.configuration.getSource().getAuth().getUsername();
        String pat = this.configuration.getSource().getAuth().getPat();

        ADOApi adoApi = new ADOApi(username, pat);

        return adoApi.getWorkItemRevisions(sourceOrg, sourceProj, startDateTime);
    }

    private void createInTarget(String wiType, String title) {
        String targetOrg = this.configuration.getTarget().getOrganisation();
        String targetProj = this.configuration.getTarget().getProject().getName();
        String username = this.configuration.getTarget().getAuth().getUsername();
        String pat = this.configuration.getTarget().getAuth().getPat();

        ADOApi adoApi = new ADOApi(username, pat);

        adoApi.createWorkItem(targetOrg, targetProj, wiType, title);
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

    private static void logAPIResponse(String response, String entity) {
        if(response != null) {
            log.trace("{} response: {}", entity, getFormattedJSON(response));
        }else {
            log.error("Failed to get {} response", entity);
        }
    }
}
