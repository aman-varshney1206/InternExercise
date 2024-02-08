package com.opshub;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;


public class ADOApiServiceTest {
    private static final String USERNAME = "aman.varshney@opshub.com";
    private static final String PAT = "";
    private static final String API_URL = "https://dev.azure.com/";

    @Test(dataProvider = "pipelineTestData")
    public void testGetPipelines(String organization, String project, boolean expectSuccess) {
        ADOConfiguration adoConfiguration = new ADOConfiguration();
        adoConfiguration.setSystemName("Azure DevOps");
        adoConfiguration.setUsername(USERNAME);
        adoConfiguration.setPassword(PAT);
        adoConfiguration.setApiUrl(API_URL);

        ADOApiService adoApiService = new ADOApiService(adoConfiguration);
        String pipelinesResponse = adoApiService.getPipelines(organization, project);

        // Assert
        if (expectSuccess) {
            Assert.assertNotNull(pipelinesResponse, "Pipelines response should not be null");
            // Add more assertions based on the expected response or behavior
        } else {
            Assert.assertNull(pipelinesResponse, "Pipelines response should be null");
            // Add more assertions based on the expected failure scenario
        }
    }

    @Test(dataProvider = "projectTestData")
    public void testGetProjects(String organization, boolean expectSuccess) {
        ADOConfiguration adoConfiguration = new ADOConfiguration();
        adoConfiguration.setSystemName("Azure DevOps");
        adoConfiguration.setUsername(USERNAME);
        adoConfiguration.setPassword(PAT);
        adoConfiguration.setApiUrl(API_URL);

        ADOApiService adoApiService = new ADOApiService(adoConfiguration);
        String projectsResponse = adoApiService.getProjects(organization);

        // Assert
        if (expectSuccess) {
            Assert.assertNotNull(projectsResponse, "Projects response should not be null");
            // Add more assertions based on the expected response or behavior
        } else {
            Assert.assertNull(projectsResponse, "Projects response should be null");
            // Add more assertions based on the expected failure scenario
        }
    }

    @DataProvider(name = "pipelineTestData")
    public Object[][] providePipelineTestData() {
        // You can add more test cases with different data
        return new Object[][]{
                {"amanvarshney0218", "ABC", true},
                // {"amanvarshney0218", "DemoProject", false},
                // Add more test cases as needed
        };
    }

    @DataProvider(name = "projectTestData")
    public Object[][] provideProjectTestData() {
        // You can add more test cases with different data
        return new Object[][]{
                {"amanvarshney0218", true},
                // {"amanvarshney0218", "DemoProject", false},
                // Add more test cases as needed
        };
    }
}

