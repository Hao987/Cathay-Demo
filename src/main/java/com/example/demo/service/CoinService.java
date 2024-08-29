package com.example.demo.service;

import com.example.demo.Entity.Coin;
import com.example.demo.Repository.CoinRepository;
import com.example.demo.conifg.CoinMapping;
import com.example.demo.exception.ApplicationException;
import com.example.demo.request.CoinInfoRequest;
import com.example.demo.dto.CoinDTO;
import com.example.demo.utils.DateUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoinService {

    final String coinDeskApi = "https://api.coindesk.com/v1/bpi/currentprice.json";

    @Autowired
    private CoinRepository coinRepository;


    public List<Coin> getAllCoins() {
        return coinRepository.findAll();
    }


    public Coin getCoinByCode(@RequestBody CoinInfoRequest coinInfoRequest) {
        return coinRepository.findCoinByCode(coinInfoRequest.getCode());
    }

    @Transactional
    public String deleteCoinByCode(@RequestBody CoinInfoRequest coinInfoRequest) {
        if (coinRepository.findCoinByCode(coinInfoRequest.getCode()) != null) {
            coinRepository.deleteByCode(coinInfoRequest.getCode());
            return ("Coin deleted successfully");
        }
        return ("Coin could not be deleted");
    }

    @Transactional
    public Coin addCoin(@RequestBody Coin coin) {
        if (coinRepository.findCoinByCode(coin.getCode()) == null) {
            checkParameter(coin);
            Coin addCoin = new Coin();
            addCoin.setCode(coin.getCode());
            addCoin.setRate(coin.getRate());
            addCoin.setRate_float(coin.getRate_float());
            addCoin.setSymbol(coin.getSymbol());
            addCoin.setDescription(coin.getDescription());
            addCoin.setCoinCn(CoinMapping.getByCode(coin.getCode()).getName());
            coinRepository.save(addCoin);
            return addCoin;
        }
        throw new ApplicationException("Coin could not be added");
    }

    @Transactional
    public Coin changeCoin(@RequestBody Coin coin) {
        checkParameter(coin);
        try {
            if (coinRepository.findCoinByCode(coin.getCode()) != null) {
                Coin coin1 = coinRepository.findCoinByCode(coin.getCode());

                if (coin.getDescription() != null) {
                    coin1.setDescription(coin.getDescription());
                }

                if (coin.getCoinCn() != null) {
                    coin1.setCoinCn(coin.getCoinCn());
                }

                if (coin.getRate_float() != null) {
                    coin1.setRate_float(coin.getRate_float());
                }

                if (coin.getRate() != null) {
                    coin1.setRate(coin.getRate());
                }

                if (coin.getSymbol() != null) {
                    coin1.setSymbol(coin.getSymbol());
                }
                coinRepository.save(coin1);
                return coin1;
            }
        } catch (ApplicationException e) {
            throw new ApplicationException("Coin could not be changed");
        }
        return null;
    }

    public String getApiInfo() throws Exception {
        // 建立連接
        HttpURLConnection connection = (HttpURLConnection) new URL(coinDeskApi).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        // 檢查回應碼
        if (connection.getResponseCode() != 200) {
            throw new RuntimeException("HTTP GET Request Failed with Error code : " + connection.getResponseCode());
        }

        // 讀取回應
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        // 關閉連接
        connection.disconnect();

        return response.toString();
    }

    public String callApi() throws Exception {
        return getApiInfo();
    }


    public List<CoinDTO> convertApi() throws Exception {
        List<CoinDTO> coinDTOs = new ArrayList<>();
        String apiInfo = getApiInfo();
        JSONObject jsonObject = new JSONObject(apiInfo);
        JSONObject bpi = jsonObject.getJSONObject("bpi");
        JSONObject time = jsonObject.getJSONObject("time");
        for (String currency : bpi.keySet()) {
            CoinDTO coinDTO = new CoinDTO();
            JSONObject currencyInfo = bpi.getJSONObject(currency);
            coinDTO.setCode(currencyInfo.getString("code"));
            coinDTO.setRate(currencyInfo.getString("rate"));
            coinDTO.setRateFloat(currencyInfo.getBigDecimal("rate_float"));
            coinDTO.setCnName(CoinMapping.getByCode(currencyInfo.getString("code")).getName());
            String updateISO = DateUtils.formatDate(time.getString("updatedISO"));
            coinDTO.setUpdateTime(updateISO);
            coinDTOs.add(coinDTO);
        }

        return coinDTOs;
    }


    public void checkParameter(Coin coin) {
        if (coin.getRate_float() == null || coin.getRate_float().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ApplicationException("missing parameter");
        }
    }
}
