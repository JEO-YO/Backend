package com.unittest.userinfosave.model.user.service;

import com.unittest.userinfosave.model.user.User;
import com.unittest.userinfosave.model.user.repository.UserRepository;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void join(User user) {
        userRepository.save(user);
    }

    @Override
    public void find(String activityArea) {
        userRepository.findNamesByActivityArea(activityArea);
    }
}
