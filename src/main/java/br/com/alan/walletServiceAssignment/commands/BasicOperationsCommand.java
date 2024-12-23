package br.com.alan.walletServiceAssignment.commands;

import br.com.alan.walletServiceAssignment.annotations.OnTopic;
import br.com.alan.walletServiceAssignment.enums.WalletOperation;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@OnTopic(name = "wallet-topic-operations")
@Builder
public record BasicOperationsCommand(
        UUID transactionId,
        String walletCode,
        BigDecimal amount,
        WalletOperation operation
) implements IBaseCommand {
}
