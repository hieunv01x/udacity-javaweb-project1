package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.common.Message;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/file")
public class FileController {

    private final UserService userService;

    private final FileService fileService;

    public FileController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @GetMapping()
    public String getFile() {
        return "redirect:/home";
    }

    @GetMapping("/download")
    public ResponseEntity downloadFile(@RequestParam("fileId") int fileId) {
        File file = fileService.getFileById(fileId);
        String filename = file.getFileName();
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(file.getFileData());
    }

    @PostMapping()
    public String uploadFile(@RequestParam("fileUpload") MultipartFile multipartFile, RedirectAttributes redirectAttributes) {
        if (multipartFile == null || multipartFile.isEmpty() || multipartFile.getOriginalFilename() == null) {
            redirectAttributes.addFlashAttribute("message", Message.FILE_IS_EMPTY.toString());
            return "redirect:/home";
        }
        Integer userId;
        try {
            userId = userService.getUserId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", Message.USER_NOT_FOUND.toString());
            return "redirect:/login";
        }
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        if (fileService.getFileByFileName(userId, fileName) != null) {
            redirectAttributes.addFlashAttribute("message", Message.FILE_IS_EXIST.toString());
            return "redirect:/home";
        }

        try {
            File file = convertToFile(fileName, multipartFile.getContentType(), String.valueOf(multipartFile.getSize()), userId, multipartFile.getBytes());
            if (fileService.saveFile(file)) {
                redirectAttributes.addFlashAttribute("message", Message.UPLOAD_FILE_SUCCESS.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("fileMessage", Message.UPLOAD_FILE_ERROR.toString());
        }
        return "redirect:/home";
    }

    @GetMapping("/delete")
    public String deleteFile(@RequestParam("fileId") int fileId, RedirectAttributes redirectAttrsAttributes) {
        if (!fileService.deleteFile(fileId)) {
            redirectAttrsAttributes.addFlashAttribute("message", Message.DELETE_FILE_ERROR.toString());
        } else {
            redirectAttrsAttributes.addFlashAttribute("message", Message.DELETE_FILE_SUCCESS.toString());
        }
        return "redirect:/home";
    }

    public File convertToFile(String fileName, String contentType, String fileSize, int userId, byte[] fileData) {
        File file = new File();
        file.setFileName(fileName);
        file.setContentType(contentType);
        file.setFileSize(fileSize);
        file.setUserId(userId);
        file.setFileData(fileData);
        return file;
    }
}