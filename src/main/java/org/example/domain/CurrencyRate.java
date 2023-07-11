package org.example.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;

@Entity
public class CurrencyRate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private String id;
    @Column
    private LocalDate date;
    @Column
    private String charCode;
    @Column
    private double rate;

    public CurrencyRate() {}
    public CurrencyRate(String id, LocalDate date, String charCode, double rate) {
        this.id = id;
        this.date = date;
        this.charCode = charCode;
        this.rate = rate;
    }
    @Override
    public String toString() {
        return "CurrencyRate{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", charCode='" + charCode + '\'' +
                ", rate=" + rate +
                '}';
    }
}