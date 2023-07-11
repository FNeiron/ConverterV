package org.example.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column
    private String id;
    @Column
    private String numCode;
    @Column
    private String charCode;
    @Column
    private int nominal;
    @Column
    private String name;

    public Currency() {}
    public Currency(String id, String numCode, String charCode, int nominal, String name) {
        this.id = id;
        this.charCode = charCode;
        this.numCode = numCode;
        this.nominal = nominal;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "id='" + id + '\'' +
                ", charCode='" + charCode + '\'' +
                ", numCode='" + numCode + '\'' +
                ", nominal=" + nominal +
                ", name='" + name + '\'' +
                '}';
    }
}
