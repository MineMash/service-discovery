package dev.minemesh.controller.configuration;

import dev.minemesh.controller.serliazier.KafkaEventSerializer;
import dev.minemesh.servicediscovery.common.event.KafkaEvent;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.kafka.KafkaConnectionDetails;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

@Configuration
public class KafkaProducerConfiguration implements ApplicationContextAware {

    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.context = applicationContext;
    }

    /**
     * Creates a {@linkplain ProducerFactory} for {@link String key}:{@link KafkaEvent event}.
     *
     * @param kafkaProperties the {@linkplain KafkaProperties}. Provided by {@linkplain org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration}.
     * @param connectionDetails the {@linkplain KafkaConnectionDetails}. Provided by {@linkplain org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration}.
     * @return the configured {@link ProducerFactory factory}.
     * @see org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration#kafkaProducerFactory(KafkaConnectionDetails, ObjectProvider) for more details.
     */
    @Bean
    public ProducerFactory<String, KafkaEvent> producerFactory(KafkaProperties kafkaProperties, KafkaConnectionDetails connectionDetails) {
        Map<String, Object> properties = kafkaProperties.buildProducerProperties();
        properties.putIfAbsent(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, connectionDetails.getProducerBootstrapServers());
        properties.putIfAbsent(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "PLAINTEXT");

        DefaultKafkaProducerFactory<String, KafkaEvent> factory = new DefaultKafkaProducerFactory<>(properties);
        factory.setKeySerializerSupplier(StringSerializer::new);
        factory.setValueSerializerSupplier(() -> context.getBean(KafkaEventSerializer.class));

        String transactionIdPrefix = kafkaProperties.getProducer().getTransactionIdPrefix();
        if (transactionIdPrefix != null) factory.setTransactionIdPrefix(transactionIdPrefix);

        return factory;
    }

    @Bean
    public KafkaTemplate<String, KafkaEvent> kafkaTemplate(ProducerFactory<String, KafkaEvent> factory) {
        return new KafkaTemplate<>(factory);
    }

}
