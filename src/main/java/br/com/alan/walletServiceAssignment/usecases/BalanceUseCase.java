package br.com.alan.walletServiceAssignment.usecases;

import br.com.alan.walletServiceAssignment.dtos.BalanceDTO;
import br.com.alan.walletServiceAssignment.services.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class BalanceUseCase {

    private final WalletService walletService;

    public BalanceDTO execute(String walletCode, LocalDateTime endDate) {
        val entity = walletService.getActive(walletCode);

        BigDecimal balance;
        if (endDate != null) {
            balance = walletService.getBalance(walletCode, endDate);
        } else
            balance = entity.getBalance();

        log.info(String.valueOf(endDate));

        return BalanceDTO
                .builder()
                .walletCode(walletCode)
                .balance(balance)
                .build();
    }
}
