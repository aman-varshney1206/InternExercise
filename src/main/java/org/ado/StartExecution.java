package org.ado;

import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.util.Scanner;

import org.ado.helpers.JsonMapper;

@Log4j2
public class StartExecution {
    public static void start() {
        try {
            JsonMapper jsonMapper = new JsonMapper();

            Configuration configuration = jsonMapper.mapJsonFromFile(
                    new File(".\\data\\SystemConfig.json"),
                    Configuration.class
            );

            // Now you can access the data using the Configuration object
            log.trace("System Name: {}", configuration.getSystemName());
            log.trace("Source Organisation: {}", configuration.getSource().getOrganisation());
            log.trace("Source Project Name: {}", configuration.getSource().getProject().getName());
            log.trace("Target Organisation: {}", configuration.getTarget().getOrganisation());
            log.trace("Target Project Name: {}", configuration.getTarget().getProject().getName());

             //Prompt the user for the startDateTime input
            Scanner scanner = new Scanner(System.in);
            System.out.print("\n\nEnter startDateTime (e.g., 2024-02-01T01:45:30 or 2024-02-01): ");
            String startDateTime = scanner.nextLine();

            ScheduleApiCall scheduleApiCall = new ScheduleApiCall(configuration);

            scheduleApiCall.scheduleAPIRequests(startDateTime, 30);

        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }
}
