package com.unittest.chatservice.user.service;

import androidx.recyclerview.widget.RecyclerView;

public interface UserService {

    void join(String email, String password);

    void auth(String email, String password);

    String getCurrentUserId();

    void createUsers(RecyclerView recyclerView);
}
