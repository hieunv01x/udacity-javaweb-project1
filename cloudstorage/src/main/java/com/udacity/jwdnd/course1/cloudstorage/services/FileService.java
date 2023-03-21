package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.File;

import java.util.List;

public interface FileService {
    File getFileByFileName(int userId, String filename);

    File getFileById(int fileId);

    List<File> getFileByUserId(int userId);

    boolean saveFile(File file);

    boolean deleteFile(int fileId);
}