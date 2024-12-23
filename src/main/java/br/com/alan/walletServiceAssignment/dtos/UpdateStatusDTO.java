package br.com.alan.walletServiceAssignment.dtos;

import br.com.alan.walletServiceAssignment.annotations.ValidWalletStatus;
import br.com.alan.walletServiceAssignment.enums.WalletStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStatusDTO {

    @NotNull
    @ValidWalletStatus
    WalletStatus status;
}
