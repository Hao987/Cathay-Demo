package com.example.demo.Entity;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "COIN")
public class Coin {

    @Id
    @GeneratedValue
    private int id;

    private String code;
    private String symbol;
    private String rate;
    private BigDecimal rate_float;
    private String description;
    private String coinCn;
}
