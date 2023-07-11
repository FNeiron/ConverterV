package org.example.controller;

import org.example.domain.CurrencyRate;
import org.example.service.CalculateService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CurrenciesController {
    final CalculateService calculateService;

    public CurrenciesController(CalculateService calculateService) {
        this.calculateService = calculateService;
    }

    @GetMapping("/currencies")
    public String currencies(Model model) {
        Iterable<CurrencyRate> currencyRates = calculateService.getAllCurrencyRatesToday();
        model.addAttribute("date", currencyRates.iterator().next().getDate());
        model.addAttribute("currencyRates", currencyRates);
        return "currencies";
    }
}
