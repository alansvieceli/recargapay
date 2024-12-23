package br.com.alan.walletServiceAssignment.usecases;

import br.com.alan.walletServiceAssignment.commands.BasicOperationsCommand;
import br.com.alan.walletServiceAssignment.dtos.TransactionValuetDTO;
import br.com.alan.walletServiceAssignment.dtos.TransferredDTO;
import br.com.alan.walletServiceAssignment.enums.WalletOperation;
import br.com.alan.walletServiceAssignment.services.WalletService;
import br.com.alan.walletServiceAssignment.services.kafka.KafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SimpleOperationUseCase {

    private final WalletService walletService;
    private final KafkaProducer kafkaProducer;

    public TransferredDTO execute(
            String walletCode,
            WalletOperation operation,
            TransactionValuetDTO dto) {

        if (WalletOperation.DEPOSIT.equals(operation)) {
            walletService.getActive(walletCode);
        } else {
            walletService.verifyWithSufficientBallance(walletCode, dto.getAmount());
        }

        val transactionId = UUID.randomUUID();

        kafkaProducer.publish(BasicOperationsCommand
                .builder()
                .transactionId(transactionId)
                .walletCode(walletCode)
                .amount(dto.getAmount())
                .operation(operation)
                .build());

        return TransferredDTO
                .builder()
                .transferId(transactionId)
                .build();

    }
}
