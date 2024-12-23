package br.com.alan.walletServiceAssignment.dtos;

import br.com.alan.walletServiceAssignment.enums.WalletStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({ "wallet-id", "status", "message", "check-status-url" })
public class CreatedDTO {
    @Builder.Default
    WalletStatus status = WalletStatus.PROCESSING;

    @JsonProperty("wallet-id")
    String walletId;

    @JsonProperty("check-status-url")
    String checkStatusUrl;

    @Builder.Default
    String message = "Wallet creation is in progress. Check the status using the provided URL.";
}