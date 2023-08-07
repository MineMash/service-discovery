package dev.minemesh.controller.repository;

import dev.minemesh.controller.model.MetadataIdentifier;
import dev.minemesh.controller.model.metadata.MetadataEntry;
import graphql.com.google.common.collect.ImmutableList;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

@Repository
public class MetadataRepositoryImpl implements MetadataRepository {

    private final ReactiveHashOperations<String, String, String> hashOps;

    public MetadataRepositoryImpl(@Qualifier("template-string-string") ReactiveRedisTemplate<String, String> reactiveRedisTemplate) {
        this.hashOps = reactiveRedisTemplate.opsForHash();
    }

    @Override
    public Mono<String> save(MetadataIdentifier id, String entity) {
        return this.hashOps.put(idToKey(id.getServiceId()), id.getKey(), entity)
                .map(ignored -> entity);
    }

    @Override
    public Flux<String> saveAll(String serivceId, Map<String, String> entities) {
        return this.hashOps.putAll(serivceId, entities)
                .thenMany(Flux.fromIterable(entities.values()));
    }

    @Override
    public Flux<String> saveAll(Publisher<Pair<MetadataIdentifier, String>> entityStream) {
        return Flux.from(entityStream).flatMap(pair -> this.save(pair.getFirst(), pair.getSecond()));
    }

    @Override
    public Mono<String> findByKey(MetadataIdentifier metadataIdentifier) {
        return this.hashOps.get(idToKey(metadataIdentifier.getServiceId()), metadataIdentifier.getKey());
    }

    @Override
    public Mono<String> findByKey(Publisher<MetadataIdentifier> id) {
        return Mono.from(id).flatMap(this::findByKey);
    }

    @Override
    public Mono<Boolean> existsByKey(MetadataIdentifier metadataIdentifier) {
        return this.hashOps.hasKey(idToKey(metadataIdentifier.getServiceId()), metadataIdentifier.getServiceId());
    }

    @Override
    public Mono<Boolean> existsByKey(Publisher<MetadataIdentifier> id) {
        return Mono.from(id).flatMap(this::existsByKey);
    }

    @Override
    public Flux<MetadataEntry> findAll(String serviceId) {
        return this.hashOps.entries(idToKey(serviceId))
                .map(entry -> new MetadataEntry(entry.getKey(), entry.getValue()));
    }

    @Override
    public Flux<MetadataEntry> findAll(Publisher<String> serviceId) {
        return Flux.from(serviceId)
                .flatMap(this::findAll);
    }

    @Override
    public Flux<MetadataEntry> findAllByKey(String serviceId, Iterable<String> keys) {
        return this.hashOps.multiGet(idToKey(serviceId), ImmutableList.copyOf(keys))
                .flatMapMany(valueList -> {
                    List<MetadataEntry> entries = new LinkedList<>();
                    List<String> keyList = ImmutableList.copyOf(keys);

                    if (keyList.size() != valueList.size())
                        return Mono.error(new IllegalStateException("Key list and value list are not equal size"));

                    for (int i = 0; i < keyList.size(); i++) {
                        MetadataEntry entry = new MetadataEntry(
                                keyList.get(i),
                                valueList.get(i));
                        entries.add(entry);
                    }

                    return Flux.fromIterable(entries);
                });
    }

    @Override
    public Flux<MetadataEntry> findAllByKey(Publisher<MetadataIdentifier> idStream) {
        return Flux.from(idStream)
                .flatMap(identifier ->
                        this.findByKey(identifier)
                                .map(value -> new MetadataEntry(identifier.getKey(), value)));
    }

    @Override
    public Mono<Long> count(String serviceId) {
        return this.hashOps.size(idToKey(serviceId));
    }

    @Override
    public Mono<Long> count(Publisher<String> serviceId) {
        return Mono.from(serviceId)
                .flatMap(this::count);
    }

    @Override
    public Mono<Void> deleteByKey(MetadataIdentifier metadataIdentifier) {
        return this.hashOps.remove(idToKey(metadataIdentifier.getServiceId()), metadataIdentifier.getKey())
                .then();
    }

    @Override
    public Mono<Void> deleteByKey(Publisher<MetadataIdentifier> id) {
        return Mono.from(id)
                .flatMap(this::deleteByKey);
    }

    @Override
    public Mono<Void> deleteAllByKey(String serviceId, Iterable<? extends String> metadataIdentifiers) {
        return this.hashOps.remove(
                idToKey(serviceId),
                StreamSupport.stream(metadataIdentifiers.spliterator(), false)
                        .toArray()
        ).then();
    }

    @Override
    public Mono<Void> deleteAll(String serviceId) {
        return this.hashOps.delete(idToKey(serviceId))
                .then();
    }

    @Override
    public Mono<Void> deleteAll(Publisher<String> serviceId) {
        return Mono.from(serviceId)
                .flatMap(this::deleteAll);
    }

    private static final String METADATA_PREFIX = "metadata:";

    private static String idToKey(String id) {
        return METADATA_PREFIX + id;
    }

    private static String keyToId(String key) {
        return key.substring(METADATA_PREFIX.length());
    }

}
