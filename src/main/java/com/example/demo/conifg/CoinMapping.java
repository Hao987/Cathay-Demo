package com.example.demo.conifg;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CoinMapping {

    USD("USD","美金"),
    GBP("GBP","英鎊"),
    EUR("EUR","歐元")
    ;


    private final String code;

    private final String name;


    public static CoinMapping getByCode(String code) {
        for (CoinMapping c : CoinMapping.values()) {
            if (c.getCode().equals(code)) {
                return c;
            }
        }
        return null;
    }
}
