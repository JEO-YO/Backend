package com.unittest.userinfosave.model.user.repository;

import com.unittest.userinfosave.model.user.User;

public interface UserRepository {

    void save(User user);

    void findNamesByActivityArea(String activityArea);
}
