package br.com.alan.walletServiceAssignment.config;

import br.com.alan.walletServiceAssignment.commands.IBaseCommand;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaConfig {

    private final KafkaProperties kafkaTopicsProperties;

    @Bean
    public ProducerFactory<String, IBaseCommand> producerFactory() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, org.springframework.kafka.support.serializer.JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configs);
    }

    @Bean
    public KafkaTemplate<String, IBaseCommand> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, IBaseCommand> kafkaListenerContainerFactory(
            ConsumerFactory<String, IBaseCommand> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, IBaseCommand> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    @Bean
    public List<NewTopic> kafkaTopics() {
        return List.of(
                new NewTopic(kafkaTopicsProperties.getWalletTopicCreate(), 2, (short) 1),
                new NewTopic(kafkaTopicsProperties.getWalletTopicOperations(), 2, (short) 1),
                new NewTopic(kafkaTopicsProperties.getWalletTopicTransfer(), 2, (short) 1),
                new NewTopic(kafkaTopicsProperties.getWalletTopicUpdatedStatus(), 2, (short) 1)
        );
    }
}
