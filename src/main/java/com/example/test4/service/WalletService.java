package com.example.test4.service;

import com.example.test4.entity_dto.Wallet;
import com.example.test4.entity_dto.WalletRequest;
import com.example.test4.exception.InsufficientFundsException;
import com.example.test4.exception.InvalidOperationException;
import com.example.test4.exception.WalletNotFoundException;
import com.example.test4.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletService {


    private final WalletRepository walletRepository;


    @Transactional
    public Double updateBalance(WalletRequest request) {
        Wallet wallet = walletRepository.findById(request.getWalletId())
                .orElseThrow(() -> new WalletNotFoundException("Кошелек не найден"));
        String operationType = request.getOperationType();
        Double amount = request.getAmount();

        if (operationType.equals("DEPOSIT")) {
            wallet.setBalance(wallet.getBalance() + amount);
        } else if (operationType.equals("WITHDRAW")) {
            if (wallet.getBalance() < amount) {
                throw new InsufficientFundsException("Недостаточно средств");
            }
            wallet.setBalance(wallet.getBalance() - amount);
        } else {
            throw new InvalidOperationException("Невалидная операция");
        }

        walletRepository.save(wallet);
        return amount;
    }

    @Transactional(readOnly = true)
    public Double getBalance(UUID walletId) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException("Кошелек не найден"));

        return wallet.getBalance();
    }

    @Transactional(readOnly = true)
    public List<Wallet> findAll() {
        return walletRepository.findAll();
    }

    @Transactional
    public Wallet createWallet(Double amount) {
        Wallet wallet = new Wallet(UUID.randomUUID(), amount);
        return walletRepository.save(wallet);
    }
}
