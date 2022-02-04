package com.unittest.userinfosave.user.repository;

import com.unittest.userinfosave.user.User;

public interface UserRepository {

    void save(User user);

    void findNamesByActivityArea(String activityArea);
}
