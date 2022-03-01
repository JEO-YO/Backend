package com.unittest.chatservice.user.service;

import androidx.recyclerview.widget.RecyclerView;

import com.unittest.chatservice.user.repository.UserRepository;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void join(String email, String password) {
        userRepository.signUp(email, password);
    }

    @Override
    public void auth(String email, String password) {
        userRepository.signIn(email, password);
    }

    @Override
    public String getCurrentUserId() {
        return userRepository.findCurrentUserId();
    }

    @Override
    public void createUsers(RecyclerView recyclerView) {
        userRepository.createUserList(recyclerView);
    }
}
