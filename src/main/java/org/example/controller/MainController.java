package org.example.controller;

import org.example.domain.Message;
import org.example.domain.User;
import org.example.repos.MessageRepo;
import org.example.service.CalculateService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class MainController {
    private final MessageRepo messageRepo;
    private final CalculateService calculateService;

    public MainController(MessageRepo messageRepo, CalculateService calculateService) {
        this.messageRepo = messageRepo;
        this.calculateService = calculateService;
    }

    @GetMapping("/")
    public String greeting() {
        return "greeting";
    }
    @GetMapping("/main")
    public String general(
            @AuthenticationPrincipal User user,
            @RequestParam(required = false) String filter, Model model) {
        Iterable<Message> messages;

        if(filter != null && !filter.isEmpty())
            messages = messageRepo.findByTagAndAuthor(filter, user);
        else
            messages = messageRepo.findByAuthor(user);
        model.addAttribute("messages", messages);

        model.addAttribute("filter", filter);
        return "main";
    }

    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal User user,
            @RequestParam String text, @RequestParam String tag, @RequestParam double amount, Model model) {

        double res = calculateService.calculate(text, tag, amount, user);
        //Message message = new Message(text, tag, amount, res, LocalDate.now(), user);

        //messageRepo.save(message);
        Iterable<Message> messages = messageRepo.findByAuthor(user);
        model.addAttribute("messages", messages);
        return "main";
    }
}