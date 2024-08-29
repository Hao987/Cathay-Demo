package com.example.demo.Controller;


import com.example.demo.Entity.Coin;

import com.example.demo.request.CoinInfoRequest;
import com.example.demo.dto.CoinDTO;
import com.example.demo.service.CoinService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

import java.util.List;


@RestController
@RequestMapping(path = "/coin")
public class CoinController {

    @Autowired
    private CoinService coinService;

    //finAll
    @GetMapping("/getAllCoin")
    public ResponseEntity<List<Coin>> getAllCoins() {
        return new ResponseEntity<>(coinService.getAllCoins(), HttpStatus.OK);
    }

    //findOne
    @PostMapping("/getCoinInfo")
    public ResponseEntity<Coin> getCoinByCode(@RequestBody CoinInfoRequest coinInfoRequest) {
        return new ResponseEntity<>(coinService.getCoinByCode(coinInfoRequest), HttpStatus.OK);
    }

    //delete
    @PostMapping("/deleteCoin")
    public ResponseEntity<String> deleteCoinByCode(@RequestBody CoinInfoRequest coinInfoRequest) {
        return new ResponseEntity<>(coinService.deleteCoinByCode(coinInfoRequest), HttpStatus.OK);
    }

    //add
    @PostMapping("/addCoin")
    @Transactional
    public ResponseEntity<Coin> addCoin(@RequestBody Coin coin) {
        return new ResponseEntity<>(coinService.addCoin(coin), HttpStatus.OK);
    }

    //change
    @PostMapping("/changeCoin")
    public ResponseEntity<Coin> changeCoin(@RequestBody Coin coin) {
        return new ResponseEntity<>(coinService.changeCoin(coin), HttpStatus.OK);
    }

    @GetMapping(value = "/getApiInfo")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> callApi() throws Exception {
        return new ResponseEntity<>(coinService.callApi(), HttpStatus.OK);
    }

    @GetMapping("/convert")
    public ResponseEntity<List<CoinDTO>> convertApi() throws Exception {
        return new ResponseEntity<>(coinService.convertApi(), HttpStatus.OK);
    }


}
