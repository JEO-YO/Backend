package com.unittest.chatservice.user.model;

public class User {

    private final String id;
    private final String email;
    private final String password;
    public static final String USER_TABLE = "ChatUsers";

    public User(String id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
