package com.udacity.jwdnd.course1.cloudstorage.services.impl;

import com.udacity.jwdnd.course1.cloudstorage.dto.UserDto;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.repository.UserRepository;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final EncryptionService encryptionService;

    public UserServiceImpl(UserRepository userRepository, EncryptionService encryptionService) {
        this.userRepository = userRepository;
        this.encryptionService = encryptionService;
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    @Override
    public boolean saveUser(User user) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedKey = Base64.getEncoder().encodeToString(salt);
        String encryptedPassword = encryptionService.encryptValue(user.getPassword(), encodedKey);
        User newUser = new User(null, user.getUsername(), encodedKey, encryptedPassword, user.getFirstName(), user.getLastName());
        return userRepository.saveUser(newUser);
    }

    @Override
    public Integer getUserId() throws Exception {
        UserDto userDto = (UserDto) SecurityContextHolder.getContext().getAuthentication().getDetails();
        return userRepository.getUserId(userDto.getUserId(), userDto.getUsername());
    }
}