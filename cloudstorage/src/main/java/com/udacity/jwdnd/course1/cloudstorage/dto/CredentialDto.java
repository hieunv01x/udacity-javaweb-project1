package com.udacity.jwdnd.course1.cloudstorage.dto;

public class CredentialDto {
    private int credentialId;

    private String url;

    private String username;

    private String encryptPassword;

    private String decryptPassword;

    public CredentialDto(int credentialId, String url, String username, String encryptPassword, String decryptPassword) {
        this.credentialId = credentialId;
        this.url = url;
        this.username = username;
        this.encryptPassword = encryptPassword;
        this.decryptPassword = decryptPassword;
    }

    public CredentialDto() {

    }

    public int getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(int credentialId) {
        this.credentialId = credentialId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEncryptPassword() {
        return encryptPassword;
    }

    public void setEncryptPassword(String encryptPassword) {
        this.encryptPassword = encryptPassword;
    }

    public String getDecryptPassword() {
        return decryptPassword;
    }

    public void setDecryptPassword(String decryptPassword) {
        this.decryptPassword = decryptPassword;
    }
}
