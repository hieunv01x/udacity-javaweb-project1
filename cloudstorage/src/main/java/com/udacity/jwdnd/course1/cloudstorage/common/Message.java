package com.udacity.jwdnd.course1.cloudstorage.common;
public enum Message {
    USER_NOT_FOUND("User not found!"),
    CREATE_USER_SUCCESS("Create user successfully!"),
    CREATE_USER_ERROR_USERNAME("The username already exists!"),
    CREATE_USER_ERROR("Have an error, please try again!"),
    CREATE_NOTE_ERROR("An error occurred while creating the note!"),
    CREATE_NOTE_SUCCESS("Create note successfully!"),
    CREATE_NOTE_ERROR_EMPTY("Please fill in the note information!"),
    UPDATE_NOTE_ERROR("An error occurred while updating the note!"),
    UPDATE_NOTE_SUCCESS("Update note successfully!"),
    DELETE_NOTE_ERROR("An error occurred while deleting the note!"),
    DELETE_NOTE_SUCCESS("Delete note successfully!"),
    FILE_IS_EMPTY("File is empty!"),
    FILE_IS_EXIST("File is exist!"),
    UPLOAD_FILE_SUCCESS("Upload file successfully!"),
    UPLOAD_FILE_ERROR("An error occurred while uploading the file!"),
    DELETE_FILE_ERROR("An error occurred while deleting the file!"),
    DELETE_FILE_SUCCESS("Delete file successfully!"),
    CREATE_CREDENTIAL_ERROR("An error occurred while creating the credential!"),
    CREATE_CREDENTIAL_SUCCESS("Create credential successfully!"),
    CREATE_CREDENTIAL_ERROR_EMPTY("Please fill in the credential information!"),
    UPDATE_CREDENTIAL_ERROR("An error occurred while updating the credential!"),
    UPDATE_CREDENTIAL_SUCCESS("Update credential successfully!"),
    DELETE_CREDENTIAL_ERROR("An error occurred while deleting the credential!"),
    DELETE_CREDENTIAL_SUCCESS("Delete credential successfully!")
    ;

    private final String message;
    Message(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}