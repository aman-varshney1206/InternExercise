package com.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.example.ADOConfiguration.*;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ADOApp {

    public static void main(String[] args) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ADOConfiguration adoConfiguration = objectMapper.readValue(new File(".\\data\\ADOConfig.json"), ADOConfiguration.class);

            if(adoConfiguration != null) {
                ApiCallScheduler apiCallScheduler = new ApiCallScheduler(adoConfiguration);
                apiCallScheduler.verifyJSONParseData();

                ADOApi adoApi = new ADOApi(adoConfiguration);
                // Prompt the user for the startDateTime input
                Scanner scanner = new Scanner(System.in);
                System.out.print("\n\nEnter startDateTime (e.g., 2024-02-01T01:45:30 or 2024-02-01): ");
                String startDateTime = scanner.nextLine();

                apiCallScheduler.scheduleAPIRequests(startDateTime, 60, adoApi);
            }else {
                log.error("Failed to create ADOConfiguration from JSON.");
            }

        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }
}
