package dev.minemesh.controller.repository;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class MetadataRepositoryImpl implements MetadataRepository {


    private final ReactiveRedisTemplate<String, String> reactiveRedisTemplate;
    private final ReactiveHashOperations<String, String, String> hashOps;

    public MetadataRepositoryImpl(@Qualifier("template-string-string") ReactiveRedisTemplate<String, String> reactiveRedisTemplate) {
        this.reactiveRedisTemplate = reactiveRedisTemplate;
        this.hashOps = reactiveRedisTemplate.opsForHash();
    }

    @Override
    public <S extends String> Mono<S> save(S entity) {
        return null;
    }

    @Override
    public <S extends String> Flux<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends String> Flux<S> saveAll(Publisher<S> entityStream) {
        return null;
    }

    @Override
    public Mono<String> findById(String s) {
        return null;
    }

    @Override
    public Mono<String> findById(Publisher<String> id) {
        return null;
    }

    @Override
    public Mono<Boolean> existsById(String s) {
        return null;
    }

    @Override
    public Mono<Boolean> existsById(Publisher<String> id) {
        return null;
    }

    @Override
    public Flux<String> findAll() {
        return null;
    }

    @Override
    public Flux<String> findAllById(Iterable<String> strings) {
        return null;
    }

    @Override
    public Flux<String> findAllById(Publisher<String> idStream) {
        return null;
    }

    @Override
    public Mono<Long> count() {
        return null;
    }

    @Override
    public Mono<Void> deleteById(String s) {
        return null;
    }

    @Override
    public Mono<Void> deleteById(Publisher<String> id) {
        return null;
    }

    @Override
    public Mono<Void> delete(String entity) {
        return null;
    }

    @Override
    public Mono<Void> deleteAllById(Iterable<? extends String> strings) {
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
