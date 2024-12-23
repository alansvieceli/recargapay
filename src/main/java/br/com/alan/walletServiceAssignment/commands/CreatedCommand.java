package br.com.alan.walletServiceAssignment.commands;

import br.com.alan.walletServiceAssignment.annotations.OnTopic;
import br.com.alan.walletServiceAssignment.enums.WalletStatus;
import lombok.Builder;

import java.util.UUID;

@OnTopic(name = "wallet-topic-create")
@Builder
public record CreatedCommand(
        UUID clientId,
        WalletStatus status,
        String walletCode,
        String description
) implements IBaseCommand {
}
