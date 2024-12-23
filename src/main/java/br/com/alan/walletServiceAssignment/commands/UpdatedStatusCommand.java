package br.com.alan.walletServiceAssignment.commands;

import br.com.alan.walletServiceAssignment.annotations.OnTopic;
import br.com.alan.walletServiceAssignment.enums.WalletStatus;
import lombok.Builder;

@OnTopic(name = "wallet-topic-updated-status")
@Builder
public record UpdatedStatusCommand(
        String walletCode,
        WalletStatus status
) implements IBaseCommand {
}
