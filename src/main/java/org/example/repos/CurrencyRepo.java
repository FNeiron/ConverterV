package org.example.repos;

import org.example.domain.Currency;
import org.springframework.data.repository.CrudRepository;

public interface CurrencyRepo extends CrudRepository<Currency, String> {
    Currency findByCharCode(String charCode);
}
