package com.example.test4.repository;

import com.example.test4.entity_dto.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface WalletRepository extends JpaRepository<Wallet, UUID> {

    @Query(nativeQuery = true, value = "SELECT * FROM wallet where wallet_id = :id")
    Optional<Wallet> findById(UUID id);
}
