package org.example.controller;

import org.example.domain.CurrencyRate;
import org.example.service.CalculateService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class CurrenciesController {
    final CalculateService calculateService;

    public CurrenciesController(CalculateService calculateService) {
        this.calculateService = calculateService;
    }

    @GetMapping("/currencies")
    public String currencies(Model model) {
        Iterable<CurrencyRate> currencyRates = calculateService.getAllCurrencyRatesByDay(null);
        model.addAttribute("date", LocalDate.now());
        model.addAttribute("maxDate", LocalDate.now());
        model.addAttribute("currencyRates", currencyRates);
        return "currencies";
    }

    @PostMapping("/currencies")
    public String update(@RequestParam LocalDate date, Model model) {
        Iterable<CurrencyRate> currencyRates = calculateService.getAllCurrencyRatesByDay(date);
        model.addAttribute("date", date);
        model.addAttribute("maxDate", LocalDate.now());
        model.addAttribute("currencyRates", currencyRates);
        return "currencies";
    }
}
