package com.unittest.chatservice.user.service;

public interface UserService {

    void join(String email, String password);

    void auth(String email, String password);
}
