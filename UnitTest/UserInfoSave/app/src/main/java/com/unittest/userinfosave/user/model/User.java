package com.unittest.userinfosave.user.model;

import java.util.Map;

public class User {

    private final Long id;
    private final String name;
    private final String university;
    private final String department;
    private final String activityArea;
    private final String interesting;

    public User(Long id, Map<UserInfo, String> userInfo) {
        this.id = id;
        this.name = userInfo.get(UserInfo.NAME);
        this.university = userInfo.get(UserInfo.UNIVERSITY);
        this.department = userInfo.get(UserInfo.DEPARTMENT);
        this.activityArea = userInfo.get(UserInfo.ACTIVITY_AREA);
        this.interesting = userInfo.get(UserInfo.INTERESTING);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUniversity() {
        return university;
    }

    public String getDepartment() {
        return department;
    }

    public String getActivityArea() {
        return activityArea;
    }

    public String getInteresting() {
        return interesting;
    }
}
