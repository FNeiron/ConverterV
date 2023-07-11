package org.example.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter @Setter
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String text;
    private String tag;
    private double firstValue;
    private double secondValue;
    private LocalDate date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;
    public Message() {}
    public Message(String text, String tag, double firstValue, double secondValue, LocalDate date, User author) {
        this.text = text;
        this.tag = tag;
        this.firstValue = firstValue;
        this.secondValue = secondValue;
        this.date = date;
        this.author = author;
    }
}
