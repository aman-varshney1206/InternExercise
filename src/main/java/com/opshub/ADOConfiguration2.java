package com.opshub;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ADOConfiguration2 {
    private String systemName;
    private String username;
    private String password;
    private String apiUrl;
    private List<Organisation> organisations;
    private List<Project> projects;
    private Map<String, Object> fields; // You can use specific classes for each type if needed
    private Mappings mappings;

    // Inner classes for nested structures
    @Setter
    @Getter
    public static class Organisation {
        private String name;
    }

    @Setter
    @Getter
    public static class Project {
        private String id;
        private String name;
        private List<Team> teams;
        private List<Area> areas;
        private List<Iteration> iterations;

        @Setter
        @Getter
        // Inner classes for nested structures
        public static class Team {
            private String id;
            private String name;
        }

        @Setter
        @Getter
        public static class Area {
            private String name;
        }

        @Setter
        @Getter
        public static class Iteration {
            private String id;
        }
    }

    @Setter
    @Getter
    public static class Mappings {
        private Map<String, String> workItems;
    }
}
