package org.example.controller;

import org.example.domain.ConvertForm;
import org.example.domain.Message;
import org.example.domain.User;
import org.example.repos.MessageRepo;
import org.example.service.CalculateService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
        model.addAttribute("currencies", calculateService.getAllCurrencies());
        model.addAttribute("filter", filter);
        model.addAttribute("maxDate", LocalDate.now());
        model.addAttribute("convertForm", new ConvertForm());
        return "main";
    }

    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal User user,
            //@RequestParam(value = "text") String text, @RequestParam String tag, @RequestParam double amount,
            @ModelAttribute ConvertForm convertForm, @RequestParam(required = false) LocalDate date, Model model) {
        double res = calculateService.calculate(convertForm.getText(), convertForm.getTag(),
                convertForm.getAmount(), user, date);
        Iterable<Message> messages = messageRepo.findByAuthor(user);
        model.addAttribute("messages", messages);
        model.addAttribute("maxDate", LocalDate.now());
        model.addAttribute("currencies", calculateService.getAllCurrencies());
        return "main";
    }
}