package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.common.Message;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/signup")
public class SignupController {

    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String viewSignupPage() {
        return "signup";
    }

    @PostMapping()
    public String signup(@ModelAttribute User user, Model model, RedirectAttributes redirectAttributes) {
        String error = null;
        if (userService.getUserByUsername(user.getUsername()) != null) {
            error = Message.CREATE_USER_ERROR_USERNAME.toString();
        } else {
            if (!userService.saveUser(user)) {
                error = Message.CREATE_USER_ERROR.toString();
            }
        }

        if (error == null) {
            redirectAttributes.addFlashAttribute("signupMsg", Message.CREATE_USER_SUCCESS.toString());
            return "redirect:/login";
        } else {
            model.addAttribute("signupErr", error);
        }
        return "signup";
    }
}