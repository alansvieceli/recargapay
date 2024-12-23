package br.com.alan.walletServiceAssignment.services.kafka;

import br.com.alan.walletServiceAssignment.commands.IBaseCommand;
import br.com.alan.walletServiceAssignment.deciders.WalletDecider;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    private final WalletDecider walletDecider;

    @KafkaListener(
            topics = "${kafka.topic.wallet-topic-create}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void listenCreate(ConsumerRecord<String, IBaseCommand> record) {
        if (record != null && record.value() != null) {

//            String eventType = new String(record.headers()
//                    .lastHeader("eventType")
//                    .value(),
//                    StandardCharsets.UTF_8);

            val payload = record.value();

            walletDecider.handle(payload);

            System.out.println("**** Received payload: " + payload);
        } else {
            System.out.println("No record received.");
        }
    }

    @KafkaListener(
            topics = "${kafka.topic.wallet-topic-updated-status}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void listenUpdate(ConsumerRecord<String, IBaseCommand> record) {
        if (record != null && record.value() != null) {
            val payload = record.value();
            walletDecider.handle(payload);
            System.out.println("**** Received payload: " + payload);
        } else {
            System.out.println("No record received.");
        }
    }

    @KafkaListener(
            topics = "${kafka.topic.wallet-topic-operations}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void listenSimpleOperations(ConsumerRecord<String, IBaseCommand> record) {
        if (record != null && record.value() != null) {
            val payload = record.value();
            walletDecider.handle(payload);
            System.out.println("**** Received payload: " + payload);
        } else {
            System.out.println("No record received.");
        }
    }

    @KafkaListener(
            topics = "${kafka.topic.wallet-topic-transfer}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void listenTransfer(ConsumerRecord<String, IBaseCommand> record) {
        if (record != null && record.value() != null) {
            val payload = record.value();
            walletDecider.handle(payload);
            System.out.println("**** Received payload: " + payload);
        } else {
            System.out.println("No record received.");
        }
    }

}

