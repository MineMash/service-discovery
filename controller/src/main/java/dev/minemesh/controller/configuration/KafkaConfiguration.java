package dev.minemesh.controller.configuration;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.Map;

@Configuration
public class KafkaConfiguration {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        return new KafkaAdmin(
                Map.of(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress)
        );
    }

    @Bean
    public NewTopic registrationTopic() {
        return new NewTopic("service-discovery:service:register", 1, (short) 1);
    }

    @Bean
    public NewTopic unregistrationTopic() {
        return new NewTopic("service-discovery:service:unregister", 1, (short) 1);
    }

    @Bean
    public NewTopic metadataUpdateTopic() {
        return new NewTopic("service-discovery:service:metadata-update", 1, (short) 1);
    }

    @Bean
    public NewTopic stateUpdateTopic() {
        return new NewTopic("service-discovery:service:state-update", 1, (short) 1);
    }

}
