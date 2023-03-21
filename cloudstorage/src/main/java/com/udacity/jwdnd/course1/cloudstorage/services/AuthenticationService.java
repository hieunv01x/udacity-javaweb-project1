package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.dto.UserDto;
import com.udacity.jwdnd.course1.cloudstorage.repository.UserRepository;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthenticationService implements AuthenticationProvider {

    private final UserRepository userRepository;
    private final EncryptionService encryptionService;

    public AuthenticationService(UserRepository userRepository, EncryptionService encryptionService) {
        this.userRepository = userRepository;
        this.encryptionService = encryptionService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user = userRepository.getUserByUsername(username);
        if (user != null) {
            String encodedSalt = user.getSalt();
            String encryptPassword = encryptionService.encryptValue(password, encodedSalt);
            if (user.getPassword().equals(encryptPassword)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
                UserDto userDto = new UserDto(user.getUserId(), user.getUsername());
                authToken.setDetails(userDto);
                return authToken;
            }
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}