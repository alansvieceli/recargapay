package br.com.alan.walletServiceAssignment.commands;

import br.com.alan.walletServiceAssignment.annotations.OnTopic;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@OnTopic(name = "wallet-topic-transfer")
@Builder
public record TransferCommand(
        UUID transactionId,
        String OriginWalletCode,
        String DestinationWalletCode,
        BigDecimal amount
) implements IBaseCommand {
}
