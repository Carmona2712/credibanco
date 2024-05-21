package com.nexos.credibanco.controller;

import com.nexos.credibanco.dto.TransactionAnulationRequest;
import com.nexos.credibanco.dto.TransactionPurchaseRequest;
import com.nexos.credibanco.model.Transaction;
import com.nexos.credibanco.service.ITransactionService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TransactionControllerTest {

    @InjectMocks
    TransactionController transactionController;

    @Mock
    ITransactionService transactionService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
    }

    @Test
    void purchase() throws Exception {
        when(transactionService.purchase(anyString(),anyLong())).thenReturn(new HashMap<>());
        mockMvc.perform(post("/transaction/purchase")
                        .content("{\"cardId\":\"123456\", \"price\":100.0}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void findById() throws Exception {
        when(transactionService.findById(anyLong())).thenReturn(new Transaction());
        mockMvc.perform(get("/transaction/123456"))
                .andExpect(status().isOk());
    }

    @Test
    void findById_NotFound() throws Exception {
        when(transactionService.findById(anyLong())).thenReturn(null);
        mockMvc.perform(get("/transaction/123456"))
                .andExpect(status().isNotFound());
    }

    @Test
    void anulation() throws Exception {
        when(transactionService.anulation(anyString(), anyLong())).thenReturn(new HashMap<>());
        mockMvc.perform(post("/transaction/anulation")
                        .content("{\"cardId\":\"123456\", \"transactionId\":123456}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}