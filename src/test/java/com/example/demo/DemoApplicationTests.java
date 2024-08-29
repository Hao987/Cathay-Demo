package com.example.demo;

import com.example.demo.Controller.CoinController;

import com.example.demo.Entity.Coin;
import com.example.demo.service.CoinService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CoinController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
class DemoApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CoinService coinService;


    @BeforeEach
    public void init(){
        Coin coin = new Coin();
    }



    @Test
    public void testGetApiInfo() throws Exception {
        String uri = "/coin/getApiInfo";
        String test = "asd";
//        MvcResult as =  mvc.perform(MockMvcRequestBuilders.get(uri).characterEncoding("UTF-8").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
//        when(coinService.getApiInfo()).thenReturn(test);
//        ResultActions perform = mvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(coinController.getApiInfo())));
        MvcResult result = mvc.perform(get(uri).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().string(test)).andReturn();
        MockHttpServletResponse response = result.getResponse();
        System.out.println(response.getContentAsString());
    }

    @Test
    public void testConvert() throws Exception {
        String uri = "/api/v1/coin/convert";

//        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).characterEncoding("UTF-8").contentType(MediaType.APPLICATION_JSON));
//        System.out.println(result.getResponse().getStatus());
//        System.out.println("1" + result.getResponse().getContentAsString());
//        System.out.println("Character Encoding: " + result.getResponse().getCharacterEncoding());
//        System.out.println("Content Length: " + result.getResponse().getContentLength());
//        System.out.println("Content Type: " + result.getResponse().getContentType());
    }

    @Test
    public void insertCoin() throws Exception {
        Coin coin = new Coin();
        coin.setRate("60,080.071");
        coin.setCode("USD");
        coin.setSymbol("&#36;");
        coin.setDescription("United States Dollar");
        coin.setRate_float(BigDecimal.valueOf(60080.0706));
//        coin.setCoinCn(Co);
        String uri = "/coin/addCoin";
        given(coinService.addCoin(coin)).willReturn(coin);
        ResultActions result = mvc.perform(post(uri).characterEncoding("UTF-8").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(coin)));
        System.out.println(result.andReturn().getResponse().getContentAsString());
    }

    @Test
    public void getAllCoin() throws Exception {
        List<Coin> coins = new ArrayList<>();
        when(coinService.getAllCoins()).thenReturn(new ArrayList<>(coins));

        ResultActions result = mvc.perform(get("/coin/getAllCoin").contentType(MediaType.APPLICATION_JSON));
        System.out.println(result.andReturn().getResponse().getContentAsString());
    }

}
