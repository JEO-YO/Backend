package com.unittest.userinfosave.user;

import java.util.Map;
import java.lang.String;

public class User {

    private final Long id;
    private final Map<UserInfo, String> userInfo;

    public User(Long id, Map<UserInfo, String> userInfo) {
        this.id = id;
        this.userInfo = userInfo;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return userInfo.get(UserInfo.NAME);
    }

    public String getUniversity() {
        return userInfo.get(UserInfo.UNIVERSITY);
    }

    public String getDepartment() {
        return userInfo.get(UserInfo.DEPARTMENT);
    }

    public String getActivityArea() {
        return userInfo.get(UserInfo.ACTIVITY_AREA);
    }

    public String getInteresting() {
        return userInfo.get(UserInfo.INTERESTING);
    }
}
