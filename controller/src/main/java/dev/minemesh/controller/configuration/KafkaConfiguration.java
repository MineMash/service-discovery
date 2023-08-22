package dev.minemesh.controller.configuration;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.autoconfigure.kafka.KafkaConnectionDetails;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.Map;

@Configuration
public class KafkaConfiguration {

    public static final String REGISTRATION_TOPIC = "service-discovery.service.register";
    public static final String UNREGISTRATION_TOPIC = "service-discovery.service.unregister";
    public static final String METADATA_UPDATE_TOPIC = "service-discovery.service.metadata-update";
    public static final String STATE_UPDATE_TOPIC = "service-discovery.service.state-update";

    @Bean
    public KafkaAdmin kafkaAdmin(KafkaProperties kafkaProperties, KafkaConnectionDetails connectionDetails) {
        Map<String, Object> properties = kafkaProperties.buildAdminProperties();
        properties.putIfAbsent(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, connectionDetails.getAdminBootstrapServers());
        properties.putIfAbsent(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "PLAINTEXT");

        KafkaAdmin kafkaAdmin = new KafkaAdmin(properties);

        KafkaProperties.Admin admin = kafkaProperties.getAdmin();
        if (admin.getCloseTimeout() != null) {
            kafkaAdmin.setCloseTimeout((int) admin.getCloseTimeout().getSeconds());
        }
        if (admin.getOperationTimeout() != null) {
            kafkaAdmin.setOperationTimeout((int) admin.getOperationTimeout().getSeconds());
        }

        properties.forEach((k, v) -> System.out.println("%s = %s".formatted(k, v)));

        kafkaAdmin.setFatalIfBrokerNotAvailable(admin.isFailFast());
        kafkaAdmin.setModifyTopicConfigs(admin.isModifyTopicConfigs());
        kafkaAdmin.setAutoCreate(admin.isAutoCreate());
        return kafkaAdmin;
    }

    @Bean
    public NewTopic registrationTopic() {
        return new NewTopic(REGISTRATION_TOPIC, 1, (short) 1);
    }

    @Bean
    public NewTopic unregistrationTopic() {
        return new NewTopic("service-discovery.service.unregister", 1, (short) 1);
    }

    @Bean
    public NewTopic metadataUpdateTopic() {
        return new NewTopic("service-discovery.service.metadata-update", 1, (short) 1);
    }

    @Bean
    public NewTopic stateUpdateTopic() {
        return new NewTopic("service-discovery.service.state-update", 1, (short) 1);
    }

}
