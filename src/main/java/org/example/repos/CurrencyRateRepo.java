package org.example.repos;

import org.example.domain.CurrencyRate;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CurrencyRateRepo extends CrudRepository<CurrencyRate, String> {
    Optional<CurrencyRate> findByCharCodeAndDate(String charCode, LocalDate date);
    CurrencyRate findByCharCode(String charCode);
    CurrencyRate findTopByOrderByIdDesc();

    List<CurrencyRate> findByDate(LocalDate date);
}