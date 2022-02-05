package com.unittest.chatservice.user.repository;

public interface UserRepository {

    void auth(String email, String password);
}
