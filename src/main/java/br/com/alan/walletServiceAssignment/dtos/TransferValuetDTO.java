package br.com.alan.walletServiceAssignment.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferValuetDTO {

    @NotNull
    @JsonProperty("destination-client-id")
    UUID destinationClientId;

    @NotBlank
    @NotNull
    @JsonProperty("destination-wallet-code")
    String destinationWalletCode;

    @NotNull(message = "Value cannot be null")
    @DecimalMin(value = "0.01", inclusive = true, message = "Value must be greater than 0")
    @JsonProperty("amount")
    BigDecimal amount;
}
