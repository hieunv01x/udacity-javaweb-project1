package com.udacity.jwdnd.course1.cloudstorage.services.impl;

import com.udacity.jwdnd.course1.cloudstorage.dto.CredentialDto;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.repository.CredentialRepository;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CredentialServiceImpl implements CredentialService {

    private final CredentialRepository credentialRepository;

    private final EncryptionService encryptionService;

    public CredentialServiceImpl(CredentialRepository credentialRepository, EncryptionService encryptionService) {
        this.credentialRepository = credentialRepository;
        this.encryptionService = encryptionService;
    }

    @Override
    public Credential getCredentialById(String credentialId) {
        return credentialRepository.getCredentialById(credentialId);
    }

    @Override
    public List<CredentialDto> getCredentialByUserId(int userId) {
        List<Credential> credentials = credentialRepository.getCredentialByUserId(userId);
        return credentials.stream().map(this::convertToCredentialDto).collect(Collectors.toList());
    }

    @Override
    public boolean saveCredential(Credential credential) {
        return credentialRepository.saveCredential(credential);
    }

    @Override
    public boolean updateCredential(Credential credential) {
        return credentialRepository.updateCredential(credential);
    }

    @Override
    public boolean deleteCredential(int credentialId) {
        return credentialRepository.deleteCredential(credentialId);
    }

    private CredentialDto convertToCredentialDto(Credential credential) {
        CredentialDto credentialDto = new CredentialDto();
        credentialDto.setCredentialId(credential.getCredentialId());
        credentialDto.setUrl(credential.getUrl());
        credentialDto.setUsername(credential.getUsername());
        credentialDto.setEncryptPassword(credential.getPassword());
        credentialDto.setDecryptPassword(encryptionService.decryptValue(credential.getPassword(), credential.getKey()));
        return credentialDto;
    }
}
