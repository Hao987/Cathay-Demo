package com.example.demo.request;

import com.sun.istack.NotNull;
import lombok.Data;


import java.math.BigDecimal;

@Data
public class CoinAddRequest {

    @NotNull
    private String code;

    private String symbol;

    @NotNull
    private String rate;

    private String description;

    @NotNull
    private BigDecimal rateFloat;
}
