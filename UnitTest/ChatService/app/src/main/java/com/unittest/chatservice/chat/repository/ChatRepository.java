package com.unittest.chatservice.chat.repository;

import com.google.firebase.database.DatabaseReference;
import com.unittest.chatservice.chat.dto.ChatData;

public interface ChatRepository {

    void saveChat(ChatData chatData);

    DatabaseReference getChatData();
}
