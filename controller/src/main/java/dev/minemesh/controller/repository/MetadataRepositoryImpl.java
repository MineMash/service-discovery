package dev.minemesh.controller.repository;

import dev.minemesh.controller.model.MetadataIdentifier;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Repository
public class MetadataRepositoryImpl implements MetadataRepository {


    private final ReactiveRedisTemplate<String, String> reactiveRedisTemplate;
    private final ReactiveHashOperations<String, String, String> hashOps;

    public MetadataRepositoryImpl(@Qualifier("template-string-string") ReactiveRedisTemplate<String, String> reactiveRedisTemplate) {
        this.reactiveRedisTemplate = reactiveRedisTemplate;
        this.hashOps = reactiveRedisTemplate.opsForHash();
    }

    @Override
    public Mono<String> save(MetadataIdentifier id, String entity) {
        return null;
    }

    @Override
    public Flux<String> saveAll(Map<MetadataIdentifier, String> entities) {
        return null;
    }

    @Override
    public Flux<String> saveAll(Publisher<Pair<MetadataIdentifier, String>> entityStream) {
        return null;
    }

    @Override
    public Mono<String> findById(MetadataIdentifier metadataIdentifier) {
        return null;
    }

    @Override
    public Mono<String> findById(Publisher<MetadataIdentifier> id) {
        return null;
    }

    @Override
    public Mono<Boolean> existsById(MetadataIdentifier metadataIdentifier) {
        return null;
    }

    @Override
    public Mono<Boolean> existsById(Publisher<MetadataIdentifier> id) {
        return null;
    }

    @Override
    public Flux<String> findAll() {
        return null;
    }

    @Override
    public Flux<String> findAllById(Iterable<MetadataIdentifier> metadataIdentifiers) {
        return null;
    }

    @Override
    public Flux<String> findAllById(Publisher<MetadataIdentifier> idStream) {
        return null;
    }

    @Override
    public Mono<Long> count() {
        return null;
    }

    @Override
    public Mono<Void> deleteById(MetadataIdentifier metadataIdentifier) {
        return null;
    }

    @Override
    public Mono<Void> deleteById(Publisher<MetadataIdentifier> id) {
        return null;
    }

    @Override
    public Mono<Void> delete(String entity) {
        return null;
    }

    @Override
    public Mono<Void> deleteAllById(Iterable<? extends MetadataIdentifier> metadataIdentifiers) {
        return null;
    }

    @Override
    public Mono<Void> deleteAll(Iterable<? extends String> entities) {
        return null;
    }

    @Override
    public Mono<Void> deleteAll(Publisher<? extends String> entityStream) {
        return null;
    }

    @Override
    public Mono<Void> deleteAll() {
        return null;
    }
}
