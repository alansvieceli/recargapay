package br.com.alan.walletServiceAssignment.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BalanceDTO {

    @JsonProperty("wallet-code")
    String walletCode;

    @JsonProperty("balance")
    BigDecimal balance;
}
