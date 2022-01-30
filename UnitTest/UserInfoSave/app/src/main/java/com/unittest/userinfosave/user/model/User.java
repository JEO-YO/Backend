package com.unittest.userinfosave.user.model;

import java.util.Map;

public class User {

    private final Long id;
    private final java.lang.String name;
    private final java.lang.String university;
    private final java.lang.String department;
    private final java.lang.String activityArea;
    private final java.lang.String interesting;

    public User(Long id, Map<UserInfo, java.lang.String> userInfo) {
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

    public java.lang.String getName() {
        return name;
    }

    public java.lang.String getUniversity() {
        return university;
    }

    public java.lang.String getDepartment() {
        return department;
    }

    public java.lang.String getActivityArea() {
        return activityArea;
    }

    public java.lang.String getInteresting() {
        return interesting;
    }
}
