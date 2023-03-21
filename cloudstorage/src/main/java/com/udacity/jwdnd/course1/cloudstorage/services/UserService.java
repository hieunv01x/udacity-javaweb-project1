package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.User;

public interface UserService {
    User getUserByUsername(String username);

    boolean saveUser(User user);

    Integer getUserId() throws Exception;

}