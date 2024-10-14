package com.example.test4.controller;

import com.example.test4.entity_dto.Wallet;
import com.example.test4.entity_dto.WalletCreateRequest;
import com.example.test4.entity_dto.WalletRequest;
import com.example.test4.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
public class WalletController {

    @Autowired
    private WalletService walletService;

    @PostMapping("/api/v1/wallet/create")
    public ResponseEntity<Wallet> createWallet(@RequestBody WalletCreateRequest request) {
        Wallet wallet = walletService.createWallet(request.getAmount());
        return ResponseEntity.ok(wallet);
    }

    @PostMapping("/api/v1/wallet")
    public ResponseEntity<Double> modifyWallet(@RequestBody WalletRequest request) {
        Double amount = walletService.updateBalance(request);
        return ResponseEntity.ok(amount);
    }

    @GetMapping("/api/v1/wallet")
    public List<Wallet> getAllWallet() {
        return walletService.findAll();
    }

    @GetMapping("/api/v1/wallet/{walletId}")
    public ResponseEntity<Double> getBalance(@PathVariable UUID walletId) {
       Double amount = walletService.getBalance(walletId);
        return ResponseEntity.ok(amount);
    }
}
