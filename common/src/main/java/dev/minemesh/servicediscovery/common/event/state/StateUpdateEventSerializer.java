package dev.minemesh.servicediscovery.common.event.state;

import dev.minemesh.servicediscovery.common.event.KafkaEvent;
import dev.minemesh.servicediscovery.common.event.KafkaEventSerializer;
import dev.minemesh.servicediscovery.common.model.ServiceState;

import java.io.*;

public class StateUpdateEventSerializer implements KafkaEventSerializer {

    public static final int SERIALIZER_ID = 0x201;

    @Override
    public int getSerializerId() {
        return SERIALIZER_ID;
    }

    @Override
    public byte[] serialize(KafkaEvent kafkaEvent) throws IOException {
        StateUpdateEvent event = (StateUpdateEvent) kafkaEvent;

        try (ByteArrayOutputStream bytesOutput = new ByteArrayOutputStream()) {
            ObjectOutputStream output = new ObjectOutputStream(bytesOutput);

            output.writeUTF(event.getServiceId());
            event.getOldState().serialize(output);
            event.getNewState().serialize(output);

            return bytesOutput.toByteArray();
        }
    }

    @Override
    public KafkaEvent deserialize(byte[] bytes) throws IOException {
        try(ObjectInputStream input = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
            String serviceId = input.readUTF();
            ServiceState oldState = ServiceState.deserializeState(input).orElseThrow(() -> new IOException("Cannot deserialize StateUpdateEvent (old state)."));
            ServiceState newState = ServiceState.deserializeState(input).orElseThrow(() -> new IOException("Cannot deserialize StateUpdateEvent (new state)."));

            return new StateUpdateEvent(serviceId, oldState, newState);
        }
    }

    @Override
    public boolean accepts(KafkaEvent event) {
        return event instanceof StateUpdateEvent;
    }
}
