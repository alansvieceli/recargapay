package br.com.alan.walletServiceAssignment.dtos;

import br.com.alan.walletServiceAssignment.enums.WalletStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatusDTO {

    @JsonProperty("wallet-code")
    String walletCode;

    @JsonProperty("status")
    WalletStatus status;
}
