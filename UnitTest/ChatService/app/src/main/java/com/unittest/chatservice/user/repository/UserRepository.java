package com.unittest.chatservice.user.repository;

import androidx.recyclerview.widget.RecyclerView;

public interface UserRepository {

    void signUp(String email, String password);

    void signIn(String email, String password);

    String findCurrentUserId();

    void createUserList(RecyclerView recyclerView);
}
