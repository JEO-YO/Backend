package com.unittest.chatservice;

import com.unittest.chatservice.chat.repository.ChatRepository;
import com.unittest.chatservice.chat.repository.FirebaseChatRepository;
import com.unittest.chatservice.chat.service.ChatService;
import com.unittest.chatservice.chat.service.ChatServiceImpl;
import com.unittest.chatservice.user.repository.FirebaseUserRepository;
import com.unittest.chatservice.user.repository.UserRepository;
import com.unittest.chatservice.user.service.UserService;
import com.unittest.chatservice.user.service.UserServiceImpl;

public class AppConfig {

    public ChatService chatService() {
        return new ChatServiceImpl(chatRepository());
    }

    public ChatRepository chatRepository() {
        return new FirebaseChatRepository();
    }

    public UserService userService() {
        return new UserServiceImpl(userRepository());
    }

    public UserRepository userRepository() {
        return new FirebaseUserRepository();
    }
}
