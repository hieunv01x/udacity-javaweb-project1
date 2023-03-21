package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.common.Message;
import com.udacity.jwdnd.course1.cloudstorage.dto.CredentialDto;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/credential")
public class CredentialController {

    private final CredentialService credentialService;

    private final UserService userService;

    private final EncryptionService encryptionService;

    public CredentialController(CredentialService credentialService, UserService userService, EncryptionService encryptionService) {
        this.credentialService = credentialService;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    @GetMapping()
    public String viewCredential() {
        return "redirect:/home";
    }

    public void createCredential(CredentialDto credentialDto, int userId, RedirectAttributes redirectAttributes) {
        Credential credential = convertToCredential(credentialDto.getUrl(), credentialDto.getUsername(), credentialDto.getDecryptPassword(), userId);
        if (!credentialService.saveCredential(credential)) {
            redirectAttributes.addFlashAttribute("message", Message.CREATE_CREDENTIAL_ERROR.toString());
        } else {
            redirectAttributes.addFlashAttribute("message", Message.CREATE_CREDENTIAL_SUCCESS.toString());
        }
    }

    @PostMapping()
    public String updateCredential(@ModelAttribute("credential") CredentialDto credentialDto, RedirectAttributes redirectAttributes) {
        if (credentialDto == null) {
            redirectAttributes.addFlashAttribute("message", Message.CREATE_CREDENTIAL_ERROR_EMPTY.toString());
            return "redirect:/home";
        }
        Integer userId;
        try {
            userId = userService.getUserId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", Message.USER_NOT_FOUND.toString());
            return "redirect:/login";
        }
        if (credentialDto.getCredentialId() != 0) {
            Credential credential = convertToCredential(credentialDto.getUrl(), credentialDto.getUsername(), credentialDto.getDecryptPassword(), userId);
            credential.setCredentialId(credentialDto.getCredentialId());
            if (!credentialService.updateCredential(credential)) {
                redirectAttributes.addFlashAttribute("message", Message.UPDATE_CREDENTIAL_ERROR.toString());
            } else {
                redirectAttributes.addFlashAttribute("message", Message.UPDATE_CREDENTIAL_SUCCESS.toString());
            }
        } else {
            createCredential(credentialDto, userId, redirectAttributes);
        }
        return "redirect:/home";
    }

    @GetMapping("/delete")
    public String deleteCredential(@RequestParam("credentialId") int credentialId, RedirectAttributes redirectAttributes) {
        if (!credentialService.deleteCredential(credentialId)) {
            redirectAttributes.addFlashAttribute("message", Message.DELETE_CREDENTIAL_ERROR.toString());
            return "redirect:/home";
        }

        redirectAttributes.addFlashAttribute("message", Message.DELETE_CREDENTIAL_SUCCESS.toString());
        return "redirect:/home";
    }

    public Credential convertToCredential(String url, String username, String decryptPassword, int userId) {
        Credential credential = new Credential();
        credential.setUrl(url);
        credential.setUsername(username);
        credential.setKey(encryptionService.generateKey());
        credential.setPassword(encryptionService.encryptValue(decryptPassword, credential.getKey()));
        credential.setUserId(userId);
        return credential;
    }
}