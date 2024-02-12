package com.example;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import com.example.ADOConfiguration.Project;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ADOApi {
    private static final String API_VERSION = "7.2-preview.1";
    private static final String AUTHORIZATION = "Authorization";
    private static final Logger LOGGER = LogManager.getLogger(com.example.ADOApi.class);

    private final String apiUrl;
    private final String basicAuthHeader;

    public ADOApi(ADOConfiguration adoConfiguration) {
        this.apiUrl = adoConfiguration.getApiUrl();
        this.basicAuthHeader = getBasicAuthHeader(adoConfiguration.getUsername(), adoConfiguration.getPassword());
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
            LOGGER.debug(response);

            if (response.statusCode() == 200) {
                return response.body();
            } else {
                LOGGER.error("API request failed. HTTP status code: {}", response.statusCode());
                return null;
            }

        } catch (Exception e) {
            LOGGER.error(e.getLocalizedMessage(), e);
            return null;
        }
    }

    public String createPipeline(String organization, String project, String repositoryId, String pipelineName) {
        try {
            String endpoint = String.format("%s/%s/_apis/pipelines?api-version=%s", organization, project, API_VERSION);
            URI uri = new URI(apiUrl + endpoint);

            // Build the request body JSON
            // Build the request body JSON
            String requestBody = String.format(
                    "{" +
                            "    \"configuration\": {" +
                            "        \"type\": \"yaml\"," +
                            "        \"path\": \"azure-pipelines.yml\"," +
                            "        \"repository\": {" +
                            "            \"id\": \"%s\"," +
                            "            \"type\": \"azureReposGit\"" +
                            "        }" +
                            "    }," +
                            "    \"folder\": \"\\\\\"," +
                            "    \"name\": \"%s\"" +
                            "}", repositoryId, pipelineName);

            System.out.println(requestBody);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .header(AUTHORIZATION, basicAuthHeader)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            LOGGER.debug(response);

            if (response.statusCode() == 200) {
                return response.body();
            } else {
                LOGGER.error("Failed to create pipeline. HTTP status code: {}", response.statusCode());
                return null;
            }

        } catch (Exception e) {
            LOGGER.error(e.getLocalizedMessage(), e);
            return null;
        }
    }

    public String createGitRepository(String organization, Project project, String repositoryName) {
        try {
            String endpoint = String.format("/%s/%s/_apis/git/repositories?api-version=%s", organization, project.getName(), API_VERSION);
            URI uri = new URI(apiUrl + endpoint);

            // Build the request body JSON
            String requestBody = String.format("{\"name\": \"%s\", \"project\": {\"id\": \"%s\"}}", repositoryName, project.getId());

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .header(AUTHORIZATION, basicAuthHeader)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            LOGGER.debug(response);

            if (response.statusCode() == 201) {
                return response.body();
            } else {
                LOGGER.error("Failed to create Git repository. HTTP status code: {}", response.statusCode());
                return null;
            }

        } catch (Exception e) {
            LOGGER.error(e.getLocalizedMessage(), e);
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

