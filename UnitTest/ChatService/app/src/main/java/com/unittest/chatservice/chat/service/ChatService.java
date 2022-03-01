package com.unittest.chatservice.chat.service;

import androidx.recyclerview.widget.RecyclerView;

public interface ChatService {

    void send(String message, String currentUserId, String userId);

    void read(String myId, String userId, RecyclerView messageView);

    void create(RecyclerView recyclerView);

    void start(String email);
}
