package com.oracle.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.oracle.customer.dto.AccountEmailDTO;
import com.oracle.customer.model.Customer;
@Configuration
public class KafkaTopicConfig {

    @Bean
    public ConsumerFactory<String, AccountEmailDTO> accountEmailConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "account-group");
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, AccountEmailDTO.class);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(),
                new JsonDeserializer<>(AccountEmailDTO.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AccountEmailDTO> accountKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, AccountEmailDTO> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(accountEmailConsumerFactory());
        return factory;
    }


    @Bean
    public ConsumerFactory<String, Customer> customerConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "customer-group");
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, Customer.class);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(),
                new JsonDeserializer<>(Customer.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Customer> customerKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Customer> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(customerConsumerFactory());
        return factory;
    }
}
