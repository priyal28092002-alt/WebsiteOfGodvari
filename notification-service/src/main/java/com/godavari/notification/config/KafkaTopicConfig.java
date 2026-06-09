package com.godavari.notification.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    public static final String ORDER_PLACED_TOPIC  = "order.placed";
    public static final String INVOICE_PAID_TOPIC  = "invoice.paid";

    @Bean
    public NewTopic orderPlacedTopic() {
        return TopicBuilder.name(ORDER_PLACED_TOPIC)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic invoicePaidTopic() {
        return TopicBuilder.name(INVOICE_PAID_TOPIC)
                .partitions(3)
                .replicas(1)
                .build();
    }
}
