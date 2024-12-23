package br.com.alan.walletServiceAssignment.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(value = "kafka.topic", ignoreInvalidFields = true)
@Data
public class KafkaProperties {

    private String walletTopicCreate;
    private String walletTopicOperations;
    private String walletTopicTransfer;
    private String walletTopicUpdatedStatus;
}
