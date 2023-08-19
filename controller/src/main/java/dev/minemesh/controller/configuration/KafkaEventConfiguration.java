package dev.minemesh.controller.configuration;

import dev.minemesh.controller.model.ServiceModel;
import dev.minemesh.servicediscovery.common.event.KafkaEventSerializationManager;
import dev.minemesh.servicediscovery.common.event.metadata.MetadataUpdateEventSerializer;
import dev.minemesh.servicediscovery.common.event.service.ServiceRegistrationEventSerializer;
import dev.minemesh.servicediscovery.common.event.service.ServiceUnregistrationEventSerializer;
import dev.minemesh.servicediscovery.common.event.state.StateUpdateEventSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaEventConfiguration {

    @Bean
    public KafkaEventSerializationManager serializationManager() {
        return new KafkaEventSerializationManager.Builder()
                .setStrategy(KafkaEventSerializationManager.SerializerSelectionStrategy.THROW_EXCEPTION)
                .addAllSerializers(
                        new ServiceRegistrationEventSerializer(ServiceModel::new),
                        new ServiceUnregistrationEventSerializer(ServiceModel::new),
                        new StateUpdateEventSerializer(),
                        new MetadataUpdateEventSerializer()
                ).createKafkaEventManager();
    }

}
