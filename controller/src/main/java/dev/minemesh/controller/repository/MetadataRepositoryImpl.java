package dev.minemesh.controller.repository;

import dev.minemesh.controller.model.metadata.IdentifiedMetadataEntry;
import dev.minemesh.controller.model.metadata.MetadataEntry;
import dev.minemesh.controller.model.metadata.MetadataIdentifier;
import graphql.com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static dev.minemesh.servicediscovery.common.util.Precheck.checkCondition;
import static dev.minemesh.servicediscovery.common.util.Precheck.checkNotNull;

@Repository
public class MetadataRepositoryImpl implements MetadataRepository {

    private final RedisTemplate<String, String> template;
    private final HashOperations<String, String, String> hashOps;

    public MetadataRepositoryImpl(@Qualifier("template-string") RedisTemplate<String, String> template) {
        this.template = template;
        this.hashOps = template.opsForHash();
    }

    @Override
    public void save(MetadataIdentifier identifier, String value) {
        checkNotNull("identifier", identifier);
        this.hashOps.put(idToKey(identifier.getServiceId()), identifier.getKey(), value);
    }

    @Override
    public void save(IdentifiedMetadataEntry entry) {
        checkNotNull("entry", entry);
        this.save(entry.getKey(), entry.getValue());
    }

    @Override
    public void save(String id, MetadataEntry entry) {
        checkNotNull("entry", entry);
        this.hashOps.put(idToKey(id), entry.getKey(), entry.getValue());
    }

    @Override
    public void saveAll(String id, Iterable<MetadataEntry> entries) {
        this.hashOps.putAll(
                idToKey(checkNotNull("id", id)),
                ImmutableMap.copyOf(checkNotNull("entries", entries))
        );
    }

    @Override
    public Optional<String> findById(MetadataIdentifier identifier) {
        checkNotNull("identifier", identifier);
        return Optional.ofNullable(
                this.hashOps.get(
                        idToKey(identifier.getServiceId()),
                        identifier.getKey()
                )
        );
    }

    @Override
    public boolean existsById(MetadataIdentifier identifier) {
        checkNotNull("identifier", identifier);
        return this.hashOps.hasKey(idToKey(identifier.getServiceId()), identifier.getKey());
    }

    @Override
    public boolean exists(String id) {
        checkNotNull("id", id);
        return this.template.hasKey(idToKey(id));
    }

    @Override
    public List<MetadataEntry> findAll(String id) {
        return this.hashOps.entries(
                idToKey(checkNotNull("id", id)))
                .entrySet()
                .stream()
                .map(entry -> new MetadataEntry(entry.getKey(), entry.getValue()))
                .toList();
    }

    @Override
    public List<IdentifiedMetadataEntry> findAllById(Collection<MetadataIdentifier> metadataIdentifiers) {
        return checkNotNull("metadataIdentifiers", metadataIdentifiers)
                .stream()
                .map(identifier -> {
                    String value = String.valueOf(this.findById(identifier));

                    return new IdentifiedMetadataEntry(identifier, value);
                })
                .toList();
    }

    @Override
    public List<MetadataEntry> findAllById(String id, List<String> keys) {
        checkNotNull("id", id);
        checkNotNull("keys", keys);
        List<String> values = this.hashOps.multiGet(idToKey(id), keys);

        checkCondition("values.size() == keys.size()", values.size() == keys.size());

        List<MetadataEntry> entries = new LinkedList<>();
        for(int i = 0; i < values.size(); i++) {
          String key = keys.get(i);
          String value = values.get(i);

          entries.add(new MetadataEntry(key, value));
        }

        return entries;
    }

    @Override
    public long count(String id) {
        return this.hashOps.size(idToKey(checkNotNull("id", id)));
    }

    @Override
    public void deleteById(MetadataIdentifier identifier) {
        checkNotNull("identifier", identifier);
        this.hashOps.delete(idToKey(identifier.getServiceId()), identifier.getKey());
    }

    @Override
    public void deleteAllById(Iterable<? extends MetadataIdentifier> metadataIdentifiers) {
        checkNotNull("metadataIdentifiers", metadataIdentifiers).forEach(this::deleteById);
    }

    @Override
    public void deleteAllById(String id, Collection<String> keys) {
        checkNotNull("id", id);
        checkNotNull("keys", keys);
        this.hashOps.delete(idToKey(id), keys.toArray());
    }

    @Override
    public void deleteAll(String id) {
        this.template.delete(id);
    }

    private static final String METADATA_PREFIX = "metadata:";

    private static String idToKey(String id) {
        return METADATA_PREFIX + id;
    }

    private static String keyToId(String key) {
        return key.substring(METADATA_PREFIX.length());
    }

}
