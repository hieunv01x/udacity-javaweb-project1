package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.dto.CredentialDto;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;

import java.util.List;

public interface CredentialService {
    Credential getCredentialById(String credentialId);

    List<CredentialDto> getCredentialByUserId(int userId);

    boolean saveCredential(Credential credential);

    boolean updateCredential(Credential credential);

    boolean deleteCredential(int credentialId);
}