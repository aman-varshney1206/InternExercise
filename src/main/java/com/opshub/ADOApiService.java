package com.opshub;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ADOApiService {
    private static final String API_VERSION = "7.2-preview.1";

    private static final Logger LOGGER = LogManager.getLogger(ADOApiService.class);

    private final String apiUrl;
    private final String basicAuthHeader;

    public ADOApiService(ADOConfiguration adoConfiguration) {
        this.apiUrl = adoConfiguration.getApiUrl();
        this.basicAuthHeader = getBasicAuthHeader(adoConfiguration.getUsername(), adoConfiguration.getPassword());
    }

    public String getPipelines(String organization, String project) {
        try {
            String endpoint = String.format("/%s/%s/_apis/pipelines?api-version=%s", organization, project, API_VERSION);
            URI uri = new URI(apiUrl + endpoint);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("Authorization", basicAuthHeader)
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            LOGGER.info(response);

            if (response.statusCode() == 200) {
                return response.body();
            } else {
                LOGGER.error("Failed to get pipelines. HTTP status code: " + response.statusCode());
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getProjects(String organization) {
        try {
            String endpoint = String.format("/%s/_apis/projects?api-version=%s", organization, API_VERSION);
            URI uri = new URI(apiUrl + endpoint);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("Authorization", basicAuthHeader)
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            LOGGER.info(response);

            if (response.statusCode() == 200) {
                return response.body();
            } else {
                LOGGER.error("Failed to get pipelines. HTTP status code: " + response.statusCode());
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getWorkItem(String organization, String project, String workItemId) {
        try {
            String endpoint = String.format("/%s/%s/_apis/wit/workitems/%s?api-version=7.2-preview.3", organization, project, workItemId);
            URI uri = new URI(apiUrl + endpoint);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("Authorization", basicAuthHeader)
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            LOGGER.info(response);

            if (response.statusCode() == 200) {
                return response.body();
            } else {
                LOGGER.error("Failed to get workitem. HTTP status code: " + response.statusCode());
                return null;
            }

        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getGitRepositories(String organization, String project) {
        try {
            String endpoint = String.format("/%s/%s/_apis/git/repositories?api-version=%s", organization, project, API_VERSION);
            URI uri = new URI(apiUrl + endpoint);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("Authorization", basicAuthHeader)
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            LOGGER.info(response);

            if (response.statusCode() == 200) {
                return response.body();
            } else {
                LOGGER.error("Failed to get git repos. HTTP status code: " + response.statusCode());
                return null;
            }

        } catch (Exception e) {
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

