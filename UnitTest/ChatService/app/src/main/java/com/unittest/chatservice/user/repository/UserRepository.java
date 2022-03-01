package com.unittest.chatservice.user.repository;

public interface UserRepository {

    void signUp(String email, String password);

    void signIn(String email, String password);

    String findCurrentUserId();
}
