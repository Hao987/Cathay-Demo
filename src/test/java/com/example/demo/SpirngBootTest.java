package com.example.demo;


import com.example.demo.Entity.Coin;
import com.example.demo.conifg.CoinMapping;
import com.example.demo.request.CoinInfoRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpirngBootTest {

    @LocalServerPort
    private int port;

    private String basUrl = "http://localhost:" + port;

    private static RestTemplate restTemplate;

    @Autowired
    private TestH2Repository testH2Repository;


    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void setUp() {
        basUrl = "http://localhost:".concat(port + "").concat("/coin");
        Coin coin = new Coin();
        coin.setRate("60,080.071");
        coin.setCode("USD");
        coin.setSymbol("&#36;");
        coin.setDescription("United States Dollar");
        coin.setRate_float(BigDecimal.valueOf(60080.0706));
        coin.setCoinCn(CoinMapping.getByCode(coin.getCode()).getName());
        testH2Repository.save(coin);


        Coin coin1 = new Coin();
        coin1.setRate("60,080.071");
        coin1.setCode("GBP");
        coin1.setSymbol("&TEST");
        coin1.setDescription("TEST");
        coin1.setRate_float(BigDecimal.valueOf(56078.4956));
        coin1.setCoinCn(CoinMapping.getByCode(coin.getCode()).getName());
        testH2Repository.save(coin1);

    }

    @Test
    public void testAddCoin() {
        System.out.println(basUrl);
        Coin coin = new Coin();
        coin.setRate("60,080.071");
        coin.setCode("USD");
        coin.setSymbol("&#36;");
        coin.setDescription("United States Dollar");
        coin.setRate_float(BigDecimal.valueOf(60080.0706));
        coin.setCoinCn(CoinMapping.getByCode(coin.getCode()).getName());
        Coin coin1 = restTemplate.postForObject(basUrl.concat("/addCoin"), coin, Coin.class);
        System.out.println(coin1);
    }

    @Test
//    @Sql(statements = "INSERT INTO COIN(id,code,symbol,rate,rate_float,description) values (1,'USD','TEST','600078',600789.1234,'TEST1')",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testGetAllCoin(){
        List<Coin> coin = restTemplate.getForObject(basUrl.concat("/getAllCoin"), List.class);
        System.out.println(coin);
    }


    @Test
    public void testGetOpenApi(){
        String forObject = restTemplate.getForObject(basUrl.concat("/getApiInfo"), String.class);
        System.out.println(forObject);
    }

    @Test
    public void testConvertApi(){
        List<Coin> forObject1 = restTemplate.getForObject(basUrl.concat("/convert"), List.class);
        System.out.println(forObject1);
    }

    @Test
    public void testGetSingleCoin(){
        CoinInfoRequest coinInfoRequest =new CoinInfoRequest();
        coinInfoRequest.setCode("USD");
        Coin forObject = restTemplate.postForObject(basUrl.concat("/getCoinInfo"),coinInfoRequest, Coin.class);
        System.out.println(forObject);
    }

    @Test
    public void testDeleteCoin(){
        CoinInfoRequest coinInfoRequest =new CoinInfoRequest();
        coinInfoRequest.setCode("USD");
        String forObject = restTemplate.postForObject(basUrl.concat("/deleteCoin"),coinInfoRequest, String.class);
        System.out.println(forObject);
    }

    @Test
    public void testChangeCoinInfo(){

//        CoinInfoRequest coinInfoRequest =new CoinInfoRequest();
//        coinInfoRequest.setCode("USD");
//        Coin forObject = restTemplate.postForObject(basUrl.concat("/getCoinInfo"),coinInfoRequest, Coin.class);
//        System.out.println(forObject);

        Coin coin = new Coin();
//        coin.setCode("USD");
//        coin.setRate("39.12");
//        coin.setRate_float(BigDecimal.valueOf(39.116789));
        Coin coin1 = restTemplate.postForObject(basUrl.concat("/changeCoin"), coin, Coin.class);
        System.out.println(coin1);
    }

}
