package org.example.controller;

import lombok.Getter;
import org.example.domain.Role;
import org.example.domain.User;
import org.example.repos.UserRepo;
import org.example.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;

@Controller
public class RegistrationController {
    @Autowired
    UserRepo userRepo;
    @Autowired
    RegistrationService registrationService;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Model model) {
        if (registrationService.register(user)) {
            return "redirect:/login";
        }
        model.addAttribute("message", "User exists!");
        return "registration";
    }
}
