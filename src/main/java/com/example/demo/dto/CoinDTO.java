package com.example.demo.dto;



import lombok.Data;


import java.math.BigDecimal;


@Data
public class CoinDTO {

    private String code;
    private String cnName;
    private String rate;
    private BigDecimal rateFloat;
    private String updateTime;
}
