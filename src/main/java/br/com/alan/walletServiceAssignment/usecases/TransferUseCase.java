package br.com.alan.walletServiceAssignment.usecases;

import br.com.alan.walletServiceAssignment.commands.TransferCommand;
import br.com.alan.walletServiceAssignment.dtos.TransferValuetDTO;
import br.com.alan.walletServiceAssignment.dtos.TransferredDTO;
import br.com.alan.walletServiceAssignment.services.WalletService;
import br.com.alan.walletServiceAssignment.services.kafka.KafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TransferUseCase {

    private final WalletService walletService;
    private final KafkaProducer kafkaProducer;

    public TransferredDTO execute(
            String walletCode,
            TransferValuetDTO dto) {

        walletService.verifyWithSufficientBallance(walletCode, dto.getAmount());

        val destinationEntity = walletService.getActive(dto.getDestinationWalletCode());

        val transactionId = UUID.randomUUID();

        kafkaProducer.publish(TransferCommand
                .builder()
                .transactionId(transactionId)
                .OriginWalletCode(walletCode)
                .DestinationWalletCode(destinationEntity.getWalletCode())
                .amount(dto.getAmount())
                .build());

        return TransferredDTO
                .builder()
                .transferId(transactionId)
                .build();


    }
}
