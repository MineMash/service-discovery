package dev.minemesh.controller.repository;

import dev.minemesh.controller.model.ServiceModel;
import dev.minemesh.controller.util.StringUtil;
import dev.minemesh.servicediscovery.common.ServiceState;
import graphql.com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.StreamSupport;

@Repository
public class ServiceRepositoryImpl implements ServiceRepository {

    private final RedisTemplate<String, ServiceModel> template;
    private final ValueOperations<String, ServiceModel> valueOperations;
    private final RedisScript<Short> counterScript;

    public ServiceRepositoryImpl(
            @Qualifier("script-get-counter") RedisScript<Short> counterScript,
            @Qualifier("template-service-model") RedisTemplate<String, ServiceModel> reactiveRedisTemplate
    ) {
        this.counterScript = counterScript;
        this.template = reactiveRedisTemplate;
        this.valueOperations = reactiveRedisTemplate.opsForValue();
    }

    @Override
    public <S extends ServiceModel> S save(S entity) {
        String id = entity.getId();
        if (id != null) {
            this.valueOperations.set(idToKey(id), entity);
            return entity;
        }

        this.template.multi();
        this.template.execute((RedisCallback<Long>) connection -> connection.serverCommands().time());
        this.template.execute(this.counterScript, List.of());
        List<Object> result = this.template.exec();

        String generatedId = StringUtil.generateIdString((Long) result.get(0), (Short) result.get(1));
        entity.setId(generatedId);

        this.valueOperations.set(idToKey(generatedId), entity);

        return entity;
    }

    @Override
    public <S extends ServiceModel> Iterable<S> saveAll(Iterable<S> entities) {
        return StreamSupport.stream(entities.spliterator(), false)
                .map(this::save)
                .toList();
    }

    @Override
    public Optional<ServiceModel> findById(String id) {
        return Optional.ofNullable(this.valueOperations.get(idToKey(id)));
    }

    @Override
    public boolean existsById(String id) {
        // Unbox may produces NPE
        return Boolean.TRUE.equals(this.template.hasKey(idToKey(id)));
    }

    @Override
    public Iterable<ServiceModel> findAll() {
        Set<String> all = this.template.keys("%s*".formatted(SERVICE_PREFIX));

        if (all == null) return List.of();

        return this.valueOperations.multiGet(all);
    }

    @Override
    public Iterable<ServiceModel> findAllById(Iterable<String> ids) {
        return this.valueOperations.multiGet(ImmutableList.copyOf(ids));
    }

    @Override
    public long count() {
        Collection<String> keys = this.template.keys("%s*".formatted(SERVICE_PREFIX));
        return keys == null ? 0 : keys.size();
    }

    @Override
    public boolean deleteById(String id) {
        return this.template.delete(idToKey(id));
    }

    @Override
    public boolean delete(ServiceModel entity) {
        return this.deleteById(entity.getId());
    }

    @Override
    public boolean deleteAllById(Iterable<? extends String> ids) {
        return this.template.delete(
                StreamSupport.stream(ids.spliterator(), false)
                        .map(ServiceRepositoryImpl::idToKey)
                        .toList()
        ) >= 1;
    }

    @Override
    public boolean deleteAll(Iterable<? extends ServiceModel> entities) {
        return this.template.delete(
                StreamSupport.stream(entities.spliterator(), false)
                        .map(entity -> idToKey(entity.getId()))
                        .toList()
        ) >= 1;
    }

    @Override
    public boolean deleteAll() {
        this.template.delete(
                this.template.keys("%s*".formatted(SERVICE_PREFIX))
        );
    }

    @Override
    public Optional<ServiceModel> updateStateById(String id, ServiceState state) {
        return this.findById(id)
                .map(service -> {
                    service.setState(state);
                    return this.save(service);
                });
    }

    private static final String SERVICE_PREFIX = "service:";

    private static String idToKey(String id) {
        return SERVICE_PREFIX + id;
    }

    private static String keyToId(String key) {
        return key.substring(SERVICE_PREFIX.length());
    }

}
