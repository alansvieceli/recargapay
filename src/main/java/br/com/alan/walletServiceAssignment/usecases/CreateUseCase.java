package br.com.alan.walletServiceAssignment.usecases;

import br.com.alan.walletServiceAssignment.commands.CreatedCommand;
import br.com.alan.walletServiceAssignment.dtos.CreateDTO;
import br.com.alan.walletServiceAssignment.dtos.CreatedDTO;
import br.com.alan.walletServiceAssignment.services.kafka.KafkaProducer;
import br.com.alan.walletServiceAssignment.utils.WalletUtils;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateUseCase {

    private final KafkaProducer kafkaProducer;

    public CreatedDTO execute(CreateDTO walletCreateDTO) {
        val code = WalletUtils.generateCodeTransaction();

        val dto = CreatedDTO
                .builder()
                .walletId(code)
                .checkStatusUrl("/wallet/" + code + "/status")
                .build();

        kafkaProducer.publish(CreatedCommand
                .builder()
                .clientId(walletCreateDTO.getClientId())
                .walletCode(code)
                .status(dto.getStatus())
                .description(walletCreateDTO.getWalletName())
                .build());

        return dto;
    }
}
