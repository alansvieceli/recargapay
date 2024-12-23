package br.com.alan.walletServiceAssignment.usecases;

import br.com.alan.walletServiceAssignment.commands.UpdatedStatusCommand;
import br.com.alan.walletServiceAssignment.dtos.UpdateStatusDTO;
import br.com.alan.walletServiceAssignment.exceptions.BackendException;
import br.com.alan.walletServiceAssignment.services.WalletService;
import br.com.alan.walletServiceAssignment.services.kafka.KafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateStatusUseCase {

    private final WalletService walletService;
    private final KafkaProducer kafkaProducer;

    public void execute(String walletCode, UpdateStatusDTO walletUpdateStatusDTO) {
        val entity = walletService.get(walletCode);

        if (entity.getStatus() == walletUpdateStatusDTO.getStatus())
            throw new BackendException("Status is already at status " + walletUpdateStatusDTO.getStatus());

        kafkaProducer.publish(UpdatedStatusCommand
                .builder()
                .walletCode(walletCode)
                .status(walletUpdateStatusDTO.getStatus())
                .build());
    }
}
