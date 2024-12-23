package br.com.alan.walletServiceAssignment.usecases;

import br.com.alan.walletServiceAssignment.dtos.StatusDTO;
import br.com.alan.walletServiceAssignment.services.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StatusUseCase {

    private final WalletService walletService;

    public StatusDTO execute(String walletId) {
        val entity = walletService.get(walletId);

        return StatusDTO
                .builder()
                .walletCode(entity.getWalletCode())
                .status(entity.getStatus())
                .build();
    }
}
