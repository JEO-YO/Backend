package com.unittest.firebasereview;

public class User {
    private String activityArea;
    private String department;
    private Long id;
    private String interesting;
    private String name;
    private String university;
    public User(){

    }

    public User(String activityArea, String department, Long id, String interesting, String name, String university) {
        this.activityArea = activityArea;
        this.department = department;
        this.id = id;
        this.interesting = interesting;
        this.name = name;
        this.university = university;
    }

    public String getActivityArea() {
        return activityArea;
    }

    public void setActivityArea(String activityArea) {
        this.activityArea = activityArea;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInteresting() {
        return interesting;
    }

    public void setInteresting(String interesting) {
        this.interesting = interesting;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }
}
