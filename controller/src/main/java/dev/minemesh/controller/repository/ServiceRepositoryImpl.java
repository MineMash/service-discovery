package dev.minemesh.controller.repository;

import dev.minemesh.controller.model.ServiceModel;
import dev.minemesh.controller.util.StringUtil;
import graphql.com.google.common.collect.ImmutableList;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.data.redis.core.script.RedisScript;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;

import java.util.Collection;
import java.util.LinkedList;
import java.util.function.Consumer;
import java.util.stream.StreamSupport;

public class ServiceRepositoryImpl implements ServiceRepository {

    private final ReactiveRedisTemplate<String, ServiceModel> template;
    private final ReactiveValueOperations<String, ServiceModel> valueOperations;
    private final RedisScript<Short> counterScript;

    public ServiceRepositoryImpl(
            @Qualifier("script-get-counter") RedisScript<Short> counterScript,
            @Qualifier("template-service-model") ReactiveRedisTemplate<String, ServiceModel> reactiveRedisTemplate
    ) {
        this.counterScript = counterScript;
        this.template = reactiveRedisTemplate;
        this.valueOperations = reactiveRedisTemplate.opsForValue();
    }


    @Override
    public <S extends ServiceModel> Mono<S> save(S entity) {
        String id = entity.getId();
        if (id != null) {
            this.valueOperations.set(idToKey(id), entity);
            return Mono.just(entity);
        }

        Mono<Long> time = this.template.createMono(connection -> connection.serverCommands().time());
        Mono<Short> counter = Mono.from(this.template.execute(this.counterScript));

        return Mono.zip(time, counter)
                // generate id
                .map(tuple -> StringUtil.generateIdString(tuple.getT1(), tuple.getT2()))
                // set id and publish entity
                .map(generatedId -> {
                    entity.setId(generatedId);
                    this.valueOperations.set(idToKey(generatedId), entity);

                    return entity;
                });
    }

    @Override
    public <S extends ServiceModel> Flux<S> saveAll(Iterable<S> entities) {
        Collection<Mono<S>> monos = new LinkedList<>();

        for (S entity : entities) {
            monos.add(this.save(entity));
        }

        return Flux.merge(monos.toArray(Mono[]::new));
    }

    @Override
    public <S extends ServiceModel> Flux<S> saveAll(Publisher<S> entityStream) {
        return Flux.from(entityStream)
                .flatMap(this::save);
    }

    @Override
    public Mono<ServiceModel> findById(String id) {
        return this.valueOperations.get(idToKey(id)).map(ser -> ser);
    }

    @Override
    public Mono<ServiceModel> findById(Publisher<String> idPublisher) {
        return Mono.from(idPublisher)
                .flatMap(this::findById);
    }

    @Override
    public Mono<Boolean> existsById(String id) {
        return this.template.hasKey(idToKey(id));
    }

    @Override
    public Mono<Boolean> existsById(Publisher<String> id) {
        return Mono.from(id)
                .flatMap(this::existsById);
    }

    @Override
    public Flux<ServiceModel> findAll() {
        Flux<String> all = this.template.keys("%s*".formatted(SERVICE_PREFIX))
                .map(ServiceRepositoryImpl::keyToId);

        return this.findAllById(all);
    }

    @Override
    public Flux<ServiceModel> findAllById(Iterable<String> strings) {
        return this.valueOperations.multiGet(ImmutableList.copyOf(strings))
                .flatMapMany(Flux::fromIterable);
    }

    @Override
    public Flux<ServiceModel> findAllById(Publisher<String> idStream) {
        return Flux.from(idStream)
                .flatMap(this::findById);
    }

    @Override
    public Mono<Long> count() {
        return this.template.keys("%s*".formatted(SERVICE_PREFIX)).count();
    }

    @Override
    public Mono<Void> deleteById(String s) {
        return this.template.delete(idToKey(s)).then();
    }

    @Override
    public Mono<Void> deleteById(Publisher<String> id) {
        return Mono.from(id).flatMap(this::deleteById);
    }

    @Override
    public Mono<Void> delete(ServiceModel entity) {
        return this.deleteById(entity.getId());
    }

    @Override
    public Mono<Void> deleteAllById(Iterable<? extends String> strings) {
        return this.template.delete(Flux.fromIterable(strings).map(ServiceRepositoryImpl::idToKey)).then();
    }

    @Override
    public Mono<Void> deleteAll(Iterable<? extends ServiceModel> entities) {
        Iterable<String> ids = StreamSupport
                .stream(entities.spliterator(), false)
                .map(ServiceModel::getId)
                .toList();
        return this.deleteAllById(ids);
    }

    @Override
    public Mono<Void> deleteAll(Publisher<? extends ServiceModel> entityStream) {
        Flux<String> keyStream = Flux.from(entityStream)
                .map(ServiceModel
                        -> idToKey(ServiceModel.getId()));

        return this.template.delete(keyStream).then();
    }

    @Override
    public Mono<Void> deleteAll() {
        Publisher<String> keys = this.template.keys("%s*".formatted(SERVICE_PREFIX));
        return this.template.delete(keys).then();
    }

    private static final String SERVICE_PREFIX = "service-";

    private static String idToKey(String id) {
        return SERVICE_PREFIX + id;
    }

    private static String keyToId(String key) {
        return key.substring(SERVICE_PREFIX.length());
    }

    private static <T, M> Subscriber<T> subscribeOnNext(Consumer<T> consumer, MonoSink<M> mono) {
        return new Subscriber<T>() {
            @Override
            public void onSubscribe(Subscription s) {}

            @Override
            public void onNext(T t) {
                consumer.accept(t);
            }

            @Override
            public void onError(Throwable t) {
                mono.error(t);
            }

            @Override
            public void onComplete() {}
        };
    }

}
