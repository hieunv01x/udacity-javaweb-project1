package com.udacity.jwdnd.course1.cloudstorage.services.impl;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.repository.FileRepository;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public File getFileById(int fileId) {
        return fileRepository.getFileById(fileId);
    }

    @Override
    public File getFileByFileName(int userId, String fileName) {
        return fileRepository.getFileByFileName(userId, fileName);
    }

    @Override
    public List<File> getFileByUserId(int userId) {
        return fileRepository.getFileByUserId(userId);
    }

    @Override
    public boolean saveFile(File file) {
        return fileRepository.saveFile(file);
    }

    @Override
    public boolean deleteFile(int fileId) {
        return fileRepository.deleteFile(fileId);
    }
}
