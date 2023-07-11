package org.example.service;

import org.example.domain.*;
import org.example.repos.CurrencyRateRepo;
import org.example.repos.CurrencyRepo;
import org.example.repos.MessageRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service("calculateService")
public class CalculateService {
    private final CurrencyRepo currencyRepo;
    private final CurrencyRateRepo currencyRateRepo;
    private final MessageRepo messageRepo;


    public CalculateService(CurrencyRepo currencyRepo, CurrencyRateRepo currencyRateRepo, MessageRepo messageRepo) {
        this.currencyRepo = currencyRepo;
        this.currencyRateRepo = currencyRateRepo;
        this.messageRepo = messageRepo;
    }

    public double calculate(String firstCurrency, String secondCurrency, double amount, User user, LocalDate localDate) {
        //Получение информации о валютах из таблицы валют БД
        if (localDate == null) localDate = LocalDate.now();
        Currency c1 = currencyRepo.findByCharCode(firstCurrency);
        int n1 = c1.getNominal();
        Currency c2 = currencyRepo.findByCharCode(secondCurrency);
        int n2 = c2.getNominal();
        LocalDate currentDate = localDate;
        CurrencyRate cr1 = null, cr2 = null;
        DataFromXML data;
        //Попытка получить курсы валют из таблицы курсов БД на сегодняшний день
        Optional<CurrencyRate> ocr1 = currencyRateRepo.findByCharCodeAndDate(firstCurrency, currentDate);
        if (ocr1.isPresent()) {
            cr1 = ocr1.get();
        } else if (firstCurrency.equals("RUB")) {
            cr1 = currencyRateRepo.findByCharCode("RUB");
        }
        Optional<CurrencyRate> ocr2 = currencyRateRepo.findByCharCodeAndDate(secondCurrency, currentDate);
        if (ocr2.isPresent()) {
            cr2 = ocr2.get();
        } else if (secondCurrency.equals("RUB")) {
            cr2 = currencyRateRepo.findByCharCode("RUB");
        }
        //Если в БД отсутствует курс валют на сегодняшний день
        if (cr1 == null || cr2 == null) {
            //Получение "самой свежей" даты из БД
            LocalDate lastBaseDate = currencyRateRepo.findTopByOrderByIdDesc().getDate();
            System.out.println("Последняя дата из БД: " + lastBaseDate);
            //Если 2 полученные даты отличаются, в таблицу курсов базы данных добавляются актуальные курсы с сайта ЦБ
            if (!localDate.equals(lastBaseDate)) {
                data = XMLService.parseRates(localDate);
                for (CurrencyRate cr : data.getCurrencyRates()) {
                    if (cr.getCharCode().equals(firstCurrency)) {
                        cr1 = cr;
                    }
                    if (cr.getCharCode().equals(secondCurrency)) {
                        cr2 = cr;
                    }
                    currencyRateRepo.save(cr);
                }
            }
            //Если 2 полученные даты совпадают, курсы валют считываются из БД
            else {
                Optional<CurrencyRate> opcr1 = currencyRateRepo.findByCharCodeAndDate(firstCurrency, localDate);
                if (opcr1.isPresent())
                    cr1 = opcr1.get();
                Optional<CurrencyRate> opcr2 = currencyRateRepo.findByCharCodeAndDate(secondCurrency, localDate);
                if (opcr2.isPresent())
                    cr2 = opcr2.get();
            }
        }
        System.out.println("cr1" + cr1 + "cr2" + cr2);
        //Вычисление резульатата конвертации
        double res = amount * cr1.getRate() / n1 / cr2.getRate() * n2;
        res = Math.round(res * 100.0) / 100.0;
        Message conversion = new Message(c1.getCharCode(), c2.getCharCode(), amount, res, currentDate, user);
        System.out.println(conversion);
        //Сохранение конвертации в таблице истории конвертаций БД
        messageRepo.save(conversion);
        return res;

    }
    public List<Message> getHistory(String firstCurrency, String secondCurrency, LocalDate date) {
        return messageRepo.findByTextAndTagAndDate(firstCurrency, secondCurrency, date);
    }
    public Iterable<Currency> getAllCurrencies(){
        return currencyRepo.findAll();
    }

    public Iterable<CurrencyRate> getAllCurrencyRatesByDay(LocalDate date){
        if(date == null) date = LocalDate.now();
        List<CurrencyRate> data = currencyRateRepo.findByDate(date);
        if(data == null || data.isEmpty())
            data = XMLService.parseRates(date).getCurrencyRates();
        return data;
    }
}