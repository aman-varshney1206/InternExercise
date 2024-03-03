package org.ado.connection;

import lombok.Getter;
import lombok.Setter;

/**
 * The factory class which sets the default parameters to use this library.
 */

@Getter @Setter
public class Connection {
    private String organization;
    private String project;
    private String personalAccessToken;

    public Connection() {
    }

    /**
     * Instantiates the class with organization name, project name and personal access token.
     *
     * @param organization        organization name
     * @param project             project name
     * @param personalAccessToken pass the personal access token
     */
    public Connection(String organization, String project, String personalAccessToken) {
        this.organization = organization;
        this.project = project;
        this.personalAccessToken = personalAccessToken;
    }

    /**
     * Instantiates the class with organization name and personal access token.
     *
     * @param organization        organization name
     * @param personalAccessToken pass the personal access token
     */
    public Connection(String organization, String personalAccessToken) {
        this(organization, null, personalAccessToken);
    }
}