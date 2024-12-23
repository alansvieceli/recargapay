package br.com.alan.walletServiceAssignment.services;

import br.com.alan.walletServiceAssignment.commands.CreatedCommand;
import br.com.alan.walletServiceAssignment.commands.BasicOperationsCommand;
import br.com.alan.walletServiceAssignment.commands.TransferCommand;
import br.com.alan.walletServiceAssignment.commands.UpdatedStatusCommand;
import br.com.alan.walletServiceAssignment.entities.Wallet;
import br.com.alan.walletServiceAssignment.entities.WalletCommand;
import br.com.alan.walletServiceAssignment.entities.WalletOperation;
import br.com.alan.walletServiceAssignment.enums.WalletStatus;
import br.com.alan.walletServiceAssignment.exceptions.BackendException;
import br.com.alan.walletServiceAssignment.repositories.WalletCommandRepository;
import br.com.alan.walletServiceAssignment.repositories.WalletOperationRepository;
import br.com.alan.walletServiceAssignment.repositories.WalletRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class WalletService {

    private final WalletCommandRepository commandRepository;
    private final WalletRepository walletRepository;
    private final WalletOperationRepository operationRepository;

    @Async
    public void create(CreatedCommand command) {
        val wallet = Wallet.builder()
                .walletCode(command.walletCode())
                .clientId(command.clientId())
                .description(command.description())
                .status(WalletStatus.ACTIVE)
                .build();

        try {
            saveWalletAndEvent(wallet, command.getClass().getSimpleName());
        } catch (Exception e) {
            log.error("Failed to create wallet for command {}: {}", command, e.getMessage(), e);
        }
    }

    @Async
    public void updateStatus(UpdatedStatusCommand command) {
        Wallet wallet = this.get(command.walletCode());
        wallet.setStatus(command.status());
        saveWalletAndEvent(wallet, command.getClass().getSimpleName());
        log.info("Successfully updated status for wallet {} to {}", command.walletCode(), command.status());
    }

    @Async
    public void simpleOperation(BasicOperationsCommand command) {

        Wallet wallet = this.getActive(command.walletCode());

        if (wallet != null) {
            if (command.operation().equals(br.com.alan.walletServiceAssignment.enums.WalletOperation.DEPOSIT)) {
                this.simpleOperation(
                        wallet,
                        command.amount(),
                        command.operation(),
                        command.transactionId());
            } else if (command.operation().equals(br.com.alan.walletServiceAssignment.enums.WalletOperation.WITHDRAW)) {
                this.withdraw(
                        wallet,
                        command.amount(),
                        command.operation(),
                        command.transactionId());
            }

        }


    }

    @Async
    public void transfer(TransferCommand command) {

        Wallet walletOrigin = walletRepository.findByWalletCodeForUpdate(command.OriginWalletCode())
                .orElse(null);

        Wallet walletDestination = walletRepository.findByWalletCodeForUpdate(command.DestinationWalletCode())
                .orElse(null);

        //withdraw
        assert walletOrigin != null;
        //deposit
        assert walletDestination != null;

        this.withdraw(
                walletOrigin,
                command.amount(),
                br.com.alan.walletServiceAssignment.enums.WalletOperation.WITHDRAW,
                command.transactionId());


        this.simpleOperation(
                walletDestination,
                command.amount(),
                br.com.alan.walletServiceAssignment.enums.WalletOperation.DEPOSIT,
                command.transactionId());
    }

    public Wallet get(String walletCode) {
        return walletRepository.findByWalletCode(walletCode)
                .orElseThrow(() -> new BackendException("Wallet not found for code: " + walletCode));
    }

    public Wallet getActive(String walletCode) {

        val entity = this.get(walletCode);

        if (!entity.getStatus().equals(WalletStatus.ACTIVE)) {
            throw new BackendException("Wallet not active for code: " + walletCode);
        }

        return entity;
    }

    public void verifyWithSufficientBallance(String walletCode, BigDecimal value) {
        val entity = this.getActive(walletCode);

        if (entity.getBalance().compareTo(value) < 0) {
            throw new BackendException("Insufficient balance");
        }

    }

    public BigDecimal getBalance(String walletCode, LocalDateTime endDate) {
        log.info(String.valueOf(endDate));
        return walletRepository.findTotalAmountByWalletCode(
                walletCode,
                endDate
        );
    }

    private void withdraw(
            Wallet wallet,
            BigDecimal amount,
            br.com.alan.walletServiceAssignment.enums.WalletOperation operation,
            UUID transactionId) {


        wallet.setBalance(wallet.getBalance().subtract(amount));
        walletRepository.save(wallet);

        val commandOp = WalletOperation.builder()
                .transactionId(transactionId)
                .walletId(wallet.getId())
                .amount(amount)
                .operation(operation)
                .build();
        operationRepository.save(commandOp);

    }

    private void simpleOperation(
            Wallet wallet,
            BigDecimal amount,
            br.com.alan.walletServiceAssignment.enums.WalletOperation operation,
            UUID transactionId) {


        wallet.setBalance(wallet.getBalance().add(amount));
        walletRepository.save(wallet);

        val commandOp = WalletOperation.builder()
                .transactionId(transactionId)
                .walletId(wallet.getId())
                .amount(amount)
                .operation(operation)
                .build();
        operationRepository.save(commandOp);

    }

    private void saveWalletAndEvent(Wallet wallet, String commandName) {
        val walletEvent = buildWalletCommand(wallet, commandName);

        val entity = walletRepository.save(wallet);

        walletEvent.setWalletId(entity.getId());
        commandRepository.save(walletEvent);


    }

    private WalletCommand buildWalletCommand(Wallet wallet, String commandName) {
        return WalletCommand.builder()
                .command(commandName)
                .walletId(wallet.getId())
                .walletCode(wallet.getWalletCode())
                .clientId(wallet.getClientId())
                .description(wallet.getDescription())
                .status(wallet.getStatus())
                .build();
    }


}