package com.opshub;

import com.other.Organisation;
import com.other.Project;

import java.util.List;
import java.util.Map;

public class ADOConfiguration {
    private String systemName;
    private String username;
    private String password;
    private String apiUrl;
    private List<Organisation> organisations;
    private List<Project> projects;
    private Map<String, Object> fields;

    // Constructors
    public ADOConfiguration() {
        // Default constructor
    }

    public ADOConfiguration(String systemName, String username, String password, String apiUrl,
                            List<Organisation> organisations, List<Project> projects, Map<String, Object> fields) {
        this.systemName = systemName;
        this.username = username;
        this.password = password;
        this.apiUrl = apiUrl;
        this.organisations = organisations;
        this.projects = projects;
        this.fields = fields;
    }

    // Getters and Setters
    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public List<Organisation> getOrganisations() {
        return organisations;
    }

    public void setOrganisations(List<Organisation> organisations) {
        this.organisations = organisations;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public Map<String, Object> getFields() {
        return fields;
    }

    public void setFields(Map<String, Object> fields) {
        this.fields = fields;
    }
}










