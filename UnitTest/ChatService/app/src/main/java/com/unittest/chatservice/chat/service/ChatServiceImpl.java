package com.unittest.chatservice.chat.service;

import androidx.recyclerview.widget.RecyclerView;

import com.unittest.chatservice.chat.dto.ChatData;
import com.unittest.chatservice.chat.repository.ChatRepository;

public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;

    public ChatServiceImpl(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Override
    public void send(String message, String myId, String userId) {
        chatRepository.saveChat(new ChatData(message, myId, userId));
    }

    @Override
    public void read(String myId, String userId, RecyclerView messageView) {
        chatRepository.readChat(myId, userId, messageView);
    }

    @Override
    public void create(RecyclerView recyclerView) {
        chatRepository.createChat(recyclerView);
    }

    @Override
    public void start(String email) {
        chatRepository.startChat(email);
    }
}
