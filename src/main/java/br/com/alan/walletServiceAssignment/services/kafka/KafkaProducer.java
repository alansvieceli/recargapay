package br.com.alan.walletServiceAssignment.services.kafka;

import br.com.alan.walletServiceAssignment.commands.IBaseCommand;
import br.com.alan.walletServiceAssignment.utils.WalletUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProducer {

//    private final KafkaProperties kafkaProperties;
    private final KafkaTemplate<String, IBaseCommand> kafkaTemplate;

    @Async
    public void publish(IBaseCommand payload) {
//        List<Header> headers = List.of(
//                new RecordHeader("eventType", eventType.getBytes(StandardCharsets.UTF_8))
//        );

        val topicName = WalletUtils.getTopicName(payload.getClass());
        val record = new ProducerRecord<>(
                topicName,
                UUID.randomUUID().toString(),
                payload);

//        headers.forEach(record.headers()::add);

        kafkaTemplate.send(record);
        log.info("Message sent to Kafka on topic [{}]", topicName);
    }
}
