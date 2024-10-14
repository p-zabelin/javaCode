package com.example.test4.entity_dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class WalletRequest {


    @JsonProperty("wallet_id")
    private UUID walletId;

    @JsonProperty("operation_type")
    private String operationType;

    private Double amount;

}
