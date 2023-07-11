package org.example.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class ConvertForm {
    private String text;
    private String tag;
    private double amount;
    private LocalDate date;
}
