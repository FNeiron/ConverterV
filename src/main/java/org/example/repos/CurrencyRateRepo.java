package org.example.repos;

import org.example.domain.CurrencyRate;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface CurrencyRateRepo extends CrudRepository<CurrencyRate, String> {
    Optional<CurrencyRate> findByCharCodeAndDate(String charCode, LocalDate date);
    CurrencyRate findByCharCode(String charCode);
    CurrencyRate findTopByOrderByIdDesc();
    CurrencyRate findFirstByCharCode(String charCode);
}