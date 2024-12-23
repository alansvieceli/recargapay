package br.com.alan.walletServiceAssignment.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionValuetDTO {

    @NotNull(message = "Value cannot be null")
    @DecimalMin(value = "0.01", inclusive = true, message = "Value must be greater than 0")
    @JsonProperty("amount")
    BigDecimal amount;
}
