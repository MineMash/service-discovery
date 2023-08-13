package dev.minemesh.servicediscovery.common.event;

import java.io.IOException;

public interface KafkaEventSerializer {

    int getSerializerId();

    byte[] serialize(KafkaEvent event) throws IOException;

    KafkaEvent deserialize(byte[] bytes) throws IOException;

    boolean accepts(KafkaEvent event);

}
