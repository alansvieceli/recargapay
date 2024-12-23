package br.com.alan.walletServiceAssignment.deciders;

import br.com.alan.walletServiceAssignment.commands.*;
import br.com.alan.walletServiceAssignment.commands.IBaseCommand;
import br.com.alan.walletServiceAssignment.services.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WalletDecider {

    private final WalletService walletService;

    public void handle(IBaseCommand command) {
        if (command instanceof CreatedCommand cmd) {
            walletService.create(cmd);
        } else if (command instanceof UpdatedStatusCommand cmd) {
            walletService.updateStatus(cmd);
        } else if (command instanceof BasicOperationsCommand cmd) {
            walletService.simpleOperation(cmd);
        } else if (command instanceof TransferCommand cmd) {
            walletService.transfer(cmd);
        } else {
            throw new IllegalArgumentException("Command not found");
        }
    }
}
