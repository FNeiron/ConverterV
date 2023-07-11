package org.example.repos;

import org.example.domain.Message;
import org.example.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

public interface MessageRepo extends CrudRepository<Message, Long> {
    List<Message> findByTag(String tag);
    List<Message> findByAuthor(User user);
    List<Message> findByTagAndAuthor(String tag, User user);

    List<Message> findByTextAndTagAndDate(String firstCurrency, String secondCurrency, LocalDate date);
}
