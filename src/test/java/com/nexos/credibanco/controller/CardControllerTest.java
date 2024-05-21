package com.nexos.credibanco.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexos.credibanco.dto.CardIdRequest;
import com.nexos.credibanco.dto.RechargeBalanceRequest;
import com.nexos.credibanco.model.Card;
import com.nexos.credibanco.repository.ICardRepository;
import com.nexos.credibanco.service.ICardService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Log4j2
@SpringBootTest
@RunWith(SpringRunner.class)
class CardControllerTest {



    @InjectMocks
    CardController cardController;

    @Mock
    ICardService cardService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(cardController).build();
    }

    @Test
    void generateCardNumber() throws Exception {
        when(cardService.generateCardNumber(anyString())).thenReturn(new HashMap<>());
        mockMvc.perform(get("/card/123456/number"))
                .andExpect(status().isCreated());
    }


    @Test
    void enrollCard() throws Exception {
        when(cardService.cardEnroll(anyString())).thenReturn(new HashMap<>());
        mockMvc.perform(post("/card/enroll")
                        .content("{\"cardId\":\"123456\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void lockCard() throws Exception {
        when(cardService.cardLock(anyString())).thenReturn("123456");
        mockMvc.perform(delete("/card/123456"))
                .andExpect(status().isOk());
    }


    @Test
    void rechargeBalance() throws Exception {
        when(cardService.rechargeBalance(anyString(), anyDouble())).thenReturn(new HashMap<>());
        mockMvc.perform(post("/card/balance")
                        .content("{\"cardId\":\"123456\", \"balance\":100.0}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void rechargeBalance_badRequest() throws Exception {
        when(cardService.rechargeBalance(anyString(), anyDouble())).thenReturn(new HashMap<>());
        mockMvc.perform(post("/card/balance")
                        .content("{\"\"\"\" \"balance\":100.0}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void balanceQuery() throws Exception {
        when(cardService.balanceQuery(anyString())).thenReturn(new HashMap<>());
        mockMvc.perform(get("/card/balance/123456"))
                .andExpect(status().isOk());
    }
}