package com.demo;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Configuration configuration = objectMapper.readValue(new File(".\\data\\Mappings.json"), Configuration.class);

            // Now you can access the configuration object
            System.out.println("System Name: " + configuration.getSystemName());
            System.out.println("Source Project Name: " + configuration.getSource().getProject().getName());
            System.out.println("Target PAT: " + configuration.getTarget().getAuth().getPat());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
