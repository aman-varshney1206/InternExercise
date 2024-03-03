package org.ado.interfaces;

import org.ado.core.types.*;
import org.ado.enums.FeatureManagement;
import org.ado.exceptions.AdoException;

import java.util.Optional;

public interface CoreDetails {
    Processes getProcesses() throws AdoException;

    OperationReference createProject(String projectName, String description) throws AdoException;

    OperationReference createProject(String projectName, String description, String sourceControlType,
                                     String templateTypeId) throws AdoException;

    OperationReference deleteProject(String projectId) throws AdoException;

    Project getProject(String projectName) throws AdoException;

    Project getProject(String projectName, boolean includeCapabilities, boolean includeHistory) throws AdoException;

    ProjectProperties getProjectProperties(String projectId) throws AdoException;

    Projects getProjects() throws AdoException;

    Projects getProjects(int skip, int top, String continuationToken,
                         boolean getDefaultTeamImageUrl, String stateFilter) throws AdoException;

    OperationReference updateProject(String projectId, Project projectParameters) throws AdoException;

    WebApiTeam createTeam(String projectName, String teamName) throws AdoException;

    Void deleteTeam(String projectName, String teamName) throws AdoException;

    Team getTeam(String projectName, String teamName) throws AdoException;

    Team getTeam(String projectName, String teamName, boolean expandIdentity) throws AdoException;

    Teams getTeams() throws AdoException;

    Teams getTeams(boolean expandIdentity, String mine, int skip, int top) throws AdoException;

    Team updateTeams(String projectName, String teamName, String description) throws AdoException;

    Optional<Boolean> getFeatureState(String projectId, FeatureManagement feature) throws AdoException;

    ProjectFeature featureToggle(String projectId, FeatureManagement feature, boolean state) throws AdoException;
}