package org.example;

import org.example.domain.Currency;
import org.example.domain.CurrencyRate;
import org.example.domain.DataFromXML;
import org.example.repos.CurrencyRateRepo;
import org.example.repos.CurrencyRepo;
import org.example.service.XMLService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.LocalDate;
import java.util.Objects;

@SpringBootApplication
@EntityScan("org.example.domain")
@EnableJpaRepositories("org.example.repos")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    ApplicationRunner init(CurrencyRepo currencyRepo, CurrencyRateRepo currencyRateRepo){
        //При запуске приложения происходит считывание актуальных курсов валют с сайта ЦБ с помощью сервиса парсинга XML
        DataFromXML data = XMLService.parseRates(LocalDate.now());
        return args -> {
            //currencyRepo.save(new Currency("1", "111", "RUB", 1, "Российский рубль"));
            //currencyRepo.saveAll(data.getCurrencies());
            if(currencyRateRepo.findByCharCodeAndDate("USD", LocalDate.now()).isEmpty()) {
                //currencyRateRepo.save(new CurrencyRate("1", LocalDate.now(), "RUB", 1.0));
                currencyRateRepo.saveAll(data.getCurrencyRates());
            }
        };
    }
}
