package com.example;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import com.example.ADOConfiguration.Project;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.log4j.Log4j2;


@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
@Log4j2
public class ADOApi {
    private static final String API_VERSION = "7.2-preview.1";
    private static final String AUTHORIZATION = "Authorization";
    private final String apiUrl;
    private final String basicAuthHeader;

    public ADOApi(ADOConfiguration adoConfiguration) {
        this.apiUrl = adoConfiguration.getApiUrl();
        this.basicAuthHeader = getBasicAuthHeader(adoConfiguration.getUsername(), adoConfiguration.getPassword());
    }

    public ADOApi(String username, String pat) {
        this.apiUrl = "https://dev.azure.com/";
        this.basicAuthHeader = getBasicAuthHeader(username, pat);
    }

    public String getPipelines(String organization, String project) {
        String endpoint = String.format("%s/%s/_apis/pipelines?api-version=%s", organization, project, API_VERSION);
        return makeApiGetRequest(endpoint);
    }

    public String getProjects(String organization) {
        String endpoint = String.format("%s/_apis/projects?api-version=%s", organization, API_VERSION);
        return makeApiGetRequest(endpoint);
    }

    public String getWorkItem(String organization, String project, String workItemId) {
        String endpoint = String.format("%s/%s/_apis/wit/workitems/%s?api-version=7.2-preview.3", organization, project, workItemId);
        return makeApiGetRequest(endpoint);
    }

    public String getGitRepositories(String organization, String project) {
        String endpoint = String.format("%s/%s/_apis/git/repositories?api-version=%s", organization, project, API_VERSION);
        return makeApiGetRequest(endpoint);
    }

    public String getWorkItemRevisions(String organization, String project, String startDateTime) {
        String endpoint = String.format("%s/%s/_apis/wit/reporting/workitemrevisions?api-version=7.1-preview.2&includeIdentityRef=true&includeTagRef=true&startDateTime=%s", organization, project, startDateTime);
        return makeApiGetRequest(endpoint);
    }


    // Generic method to do get api call
    private String makeApiGetRequest(String endpoint) {
        try {
            URI uri = new URI(apiUrl + endpoint);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .header(AUTHORIZATION, basicAuthHeader)
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            log.debug(response);
            System.out.println(response.body());

            if (response.statusCode() == 200) {
                return response.body();
            } else {
                log.error("API request failed. HTTP status code: {}", response.statusCode());
                return null;
            }

        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            return null;
        }
    }

    public String createPipeline(String organization, String project, String repositoryId, String pipelineName) {
        String endpoint = String.format("%s/%s/_apis/pipelines?api-version=%s", organization, project, API_VERSION);

        // Build the request body JSON using Jackson
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode requestBody = objectMapper.createObjectNode();
        requestBody.putObject("configuration")
                .put("type", "yaml")
                .put("path", "azure-pipelines.yml")
                .putObject("repository")
                .put("id", repositoryId)
                .put("type", "azureReposGit");
        requestBody.put("folder", "\\\\");
        requestBody.put("name", pipelineName);

        return sendPostRequest(endpoint, requestBody.toString(), "application/json");
    }

    public String createGitRepository(String organization, Project project, String repositoryName) {
        String endpoint = String.format("%s/_apis/git/repositories?api-version=%s", organization, API_VERSION);

        // Build the request body JSON using Jackson
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode requestBody = objectMapper.createObjectNode();
        requestBody.put("name", repositoryName);
        requestBody.putObject("project").put("id", project.getId());

        return sendPostRequest(endpoint, requestBody.toString(), "application/json");
    }

    public String createWorkItem(String organization, String project, String workItemType, String title) {
        String endpoint = String.format("%s/%s/_apis/wit/workitems/$%s?api-version=7.1-preview.3",
                organization, project, workItemType);

        // Build the request body JSON using Jackson
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode jsonPatchOperations = objectMapper.createArrayNode();

        // Operation 1: Add System.Title
        jsonPatchOperations.add(createAddOperation("/fields/System.Title", title));

        // Operation 2: Add System.State
        // jsonPatchOperations.add(createAddOperation("/fields/System.State", "Proposed"));

        System.out.println(jsonPatchOperations.toString());

        return sendPostRequest(endpoint, jsonPatchOperations.toString(), "application/json-patch+json");
    }

    private ObjectNode createAddOperation(String path, String value) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode addOperation = objectMapper.createObjectNode();
        addOperation.put("op", "add");
        addOperation.put("path", path);
        addOperation.putNull("from");
        addOperation.put("value", value);
        return addOperation;
    }


    public String sendPostRequest(String endpoint, String requestBody, String contentType) {
        try {
            URI uri = new URI(apiUrl + endpoint);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .header(AUTHORIZATION, basicAuthHeader)
                    .header("Content-Type", contentType)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            log.info(response);
            System.out.println(response.body());

            if (response.statusCode() == 200 || response.statusCode() == 201) {
                return response.body();
            } else {
                System.out.println(response.body());
                log.error("Failed to perform POST request. HTTP status code: {}", response.statusCode());
                return null;
            }

        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            e.printStackTrace();
            return null;
        }
    }



    // Helper method to create Basic Authentication header
    private String getBasicAuthHeader(String username, String password) {
        String credentials = username + ":" + password;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
        return "Basic " + encodedCredentials;
    }
}

