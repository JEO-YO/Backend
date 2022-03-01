package com.unittest.chatservice.chat.repository;

import androidx.recyclerview.widget.RecyclerView;

import com.unittest.chatservice.chat.dto.ChatData;

public interface ChatRepository {

    void saveChat(ChatData chatData);

    void readChat(String myId, String userId, RecyclerView messageView);

    void createChat(RecyclerView recyclerView);

    void startChat(String email);
}
