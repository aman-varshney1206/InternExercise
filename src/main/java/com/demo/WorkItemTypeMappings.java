package com.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.runners.Suite;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

public class WorkItemTypeMappings {

    private static final String BASE_URL = "https://dev.azure.com/amanvarshney0218";
    private static final String PROJECT_NAME = "sampl456";
    private static final String API_VERSION = "7.1-preview.3";

    public static void main(String[] args) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            // Create the root node
            ObjectNode rootNode = objectMapper.createObjectNode();
            ObjectNode entityMappingsNode = rootNode.putObject("entityMappings");
            ObjectNode fieldMappingsNode = rootNode.putObject("fieldMappings");
            ObjectNode valueMappingsNode = rootNode.putObject("valueMappings");

            // Define your work item types
            List<String> workItemTypes = List.of("Bug", "Change%20Request", "Code%20Review%20Request", "Code%20Review%20Response", "Epic", "Feature", "Feedback%20Request", "Feedback%20Response", "Issue", "Requirement", "Review", "Risk", "Shared%20Parameter", "Shared%20Steps", "Task", "Test%20Case", "Test%20Plan", "Test%20Suite");

            for (String entityType : workItemTypes) {
                // Fetch fields for the current work item type
                List<String> fields = fetchFieldsForEntityType(entityType);

                // Add entity mapping
                entityMappingsNode.put(entityType, entityType);
                ObjectNode valueEntityNode = valueMappingsNode.putObject(entityType);

                // Add field mappings
                ObjectNode fieldNode = fieldMappingsNode.putObject(entityType);
                for (String field : fields) {
                    fieldNode.put(field, field);

                    // Check if the field has allowed values

                    List<String> allowedValues = fetchAllowedValuesForField(entityType, field);
                    System.out.println("Allowed values: "+allowedValues);
                    if (!allowedValues.isEmpty()) {
                        // Add value mappings
                        ObjectNode valueNode = valueEntityNode.putObject(field);
                        for (String value : allowedValues) {
                            valueNode.put(value, value);
                        }
                    }
                }
            }

            // Write the JSON to a file
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("workItemTypeMappings.json"), rootNode);

            System.out.println("Work item type mappings saved to workItemTypeMappings.json");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static List<String> fetchFieldsForEntityType(String entityType) throws IOException, InterruptedException {
        String endpoint = String.format("/%s/_apis/wit/workitemtypes/%s/fields?api-version=%s&$expand=All",
                PROJECT_NAME, entityType, API_VERSION);

        // Make API request to fetch fields
        String response = makeApiGetRequest(endpoint);

        // Parse the response to extract field names
        return parseFieldNamesFromResponse(response);
    }

    private static List<String> parseFieldNamesFromResponse(String response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode fieldsNode = (ArrayNode) objectMapper.readTree(response).get("value");

        List<String> fieldNames = new ArrayList<>();
        for (var fieldNode : fieldsNode) {
            fieldNames.add(fieldNode.get("referenceName").asText());
        }

        return fieldNames;
    }

    private static List<String> fetchAllowedValuesForField(String entityType, String field)
            throws IOException, InterruptedException {
        String endpoint = String.format("/%s/_apis/wit/workitemtypes/%s/fields/%s?api-version=%s&$expand=All",
                PROJECT_NAME, entityType, field, API_VERSION);

        // Make API request to fetch field details
        String response = makeApiGetRequest(endpoint);

        // Parse the response to extract allowed values
        return parseAllowedValuesFromResponse(response);
    }

    private static List<String> parseAllowedValuesFromResponse(String response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode allowedValuesNode = (ArrayNode) objectMapper.readTree(response).get("allowedValues");

        List<String> allowedValues = new ArrayList<>();
        if(allowedValuesNode != null){
            for (var valueNode : allowedValuesNode) {
                allowedValues.add(valueNode.asText());
            }
        }


        return allowedValues;
    }

    private static String makeApiGetRequest(String endpoint) {
        try {
            URI uri = new URI(BASE_URL + endpoint);
            String basicAuthHeader = getBasicAuthHeader("aman.varshney@opshub.com", "hwligrtzaavewae2onkiwl6nkkpcr7fgv3gzwrd6ako5bzyowwgq");

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("Authorization", basicAuthHeader)
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response);
            System.out.println(response.body());

            if (response.statusCode() == 200) {
                return response.body();
            } else {
                System.out.println("API request failed. HTTP status code: {}"+ response.statusCode());
                return null;
            }

        } catch (Exception e) {
            //(e.getLocalizedMessage(), e);
            e.printStackTrace();
            return null;
        }
    }

    private static String getBasicAuthHeader(String username, String password) {
        String credentials = username + ":" + password;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
        return "Basic " + encodedCredentials;
    }
}
