package com.other;

import java.util.List;

public class Project {
    private String id;
    private String name;
    private List<Team> teams;
    private List<Area> areas;
    private List<Iteration> iterations;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public List<Area> getAreas() {
        return areas;
    }

    public List<Iteration> getIterations() {
        return iterations;
    }
}