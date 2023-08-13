package dev.minemesh.controller.configuration;

import dev.minemesh.controller.serliazier.KafkaEventSerializer;
import dev.minemesh.servicediscovery.common.event.KafkaEvent;
import dev.minemesh.servicediscovery.common.event.KafkaEventSerializationManager;
import dev.minemesh.servicediscovery.common.model.RegisteredService;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

import static org.apache.kafka.clients.producer.ProducerConfig.*;

@Configuration
public class KafkaProducerConfiguration implements ApplicationContextAware {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.context = applicationContext;
    }

    @Bean
    public ProducerFactory<String, KafkaEvent> producerFactory() {
        Map<String, Object> configProps = Map.of(BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);

        return new DefaultKafkaProducerFactory<>(
                configProps,
                StringSerializer::new,
                () -> new KafkaEventSerializer(this.context.getBean(KafkaEventSerializationManager.class))
        );
    }

    @Bean
    public KafkaTemplate<String, KafkaEvent> kafkaTemplate(ProducerFactory<String, KafkaEvent> factory) {
        return new KafkaTemplate<>(factory);
    }

}
