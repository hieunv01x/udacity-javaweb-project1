package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.common.Message;
import com.udacity.jwdnd.course1.cloudstorage.dto.CredentialDto;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final UserService userService;

    private final NoteService noteService;

    private final FileService fileService;

    private final CredentialService credentialService;

    public HomeController(UserService userService, NoteService noteService, FileService fileService, CredentialService credentialService) {
        this.userService = userService;
        this.noteService = noteService;
        this.fileService = fileService;
        this.credentialService = credentialService;
    }

    @GetMapping()
    public String viewHomePage(Model model, RedirectAttributes redirectAttributes) {
        Integer userId;
        try {
            userId = userService.getUserId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", Message.USER_NOT_FOUND.toString());
            return "redirect:/login";
        }

        List<Note> noteList = noteService.getNoteByUserId(userId);
        List<CredentialDto> credentialList = credentialService.getCredentialByUserId(userId);
        List<File> fileList = fileService.getFileByUserId(userId);

        Map<String, Object> map = new HashMap<>();
        map.put("noteList", noteList);
        map.put("credentialList", credentialList);
        map.put("fileList", fileList);

        model.addAllAttributes(map);
        return "home";
    }
}