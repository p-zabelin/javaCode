package com.example.test4;

import com.example.test4.controller.WalletController;
import com.example.test4.entity_dto.Wallet;
import com.example.test4.entity_dto.WalletCreateRequest;
import com.example.test4.entity_dto.WalletRequest;
import com.example.test4.service.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WalletController.class)
public class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WalletService walletService;

    private Wallet wallet;

    @BeforeEach
    void setUp() {
        wallet = new Wallet(UUID.randomUUID(), 100.0);
    }

    @Test
    void testCreateWallet() throws Exception {
        WalletCreateRequest request = new WalletCreateRequest();
        request.setAmount(100.0);

        when(walletService.createWallet(anyDouble())).thenReturn(wallet);

        mockMvc.perform(post("/api/v1/wallet/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\": 100.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.walletId").value(wallet.getWalletId().toString()))
                .andExpect(jsonPath("$.balance").value(wallet.getBalance()));
    }

    @Test
    void testModifyWalletDeposit() throws Exception {
        WalletRequest request = new WalletRequest();
        request.setWalletId(wallet.getWalletId());
        request.setOperationType("DEPOSIT");
        request.setAmount(50.0);

        when(walletService.updateBalance(any(WalletRequest.class))).thenReturn(50.0);

        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"wallet_id\": \"" + wallet.getWalletId() + "\", \"operation_type\": \"DEPOSIT\", \"amount\": 50.0}"))
                .andExpect(status().isOk())
                .andExpect(content().string("50.0"));
    }

    @Test
    void testModifyWalletWithdraw() throws Exception {
        WalletRequest request = new WalletRequest();
        request.setWalletId(wallet.getWalletId());
        request.setOperationType("WITHDRAW");
        request.setAmount(50.0);

        when(walletService.updateBalance(any(WalletRequest.class))).thenReturn(50.0);

        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"wallet_id\": \"" + wallet.getWalletId() + "\", \"operation_type\": \"WITHDRAW\", \"amount\": 50.0}"))
                .andExpect(status().isOk())
                .andExpect(content().string("50.0"));
    }

    @Test
    void testGetAllWallets() throws Exception {
        when(walletService.findAll()).thenReturn(Collections.singletonList(wallet));

        mockMvc.perform(get("/api/v1/wallet"))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].walletId").value(wallet.getWalletId().toString()))
                .andExpect(jsonPath("$[0].balance").value(wallet.getBalance()));
    }

    @Test
    void testGetBalance() throws Exception {
        when(walletService.getBalance(wallet.getWalletId())).thenReturn(wallet.getBalance());

        mockMvc.perform(get("/api/v1/wallet/" + wallet.getWalletId()))
                .andExpect(status().isOk())
                .andExpect(content().string(wallet.getBalance().toString()));
    }
}

