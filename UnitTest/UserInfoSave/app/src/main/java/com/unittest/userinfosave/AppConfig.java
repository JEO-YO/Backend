package com.unittest.userinfosave;

import com.unittest.userinfosave.user.service.UserService;
import com.unittest.userinfosave.user.service.UserServiceImpl;
import com.unittest.userinfosave.user.repository.FirebaseUserRepository;
import com.unittest.userinfosave.user.repository.UserRepository;

public class AppConfig {

    public UserService userService() {
        return new UserServiceImpl(userRepository());
    }

    public UserRepository userRepository() {
        return new FirebaseUserRepository();
    }
}
