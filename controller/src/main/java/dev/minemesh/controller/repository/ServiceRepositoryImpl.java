package dev.minemesh.controller.repository;

import dev.minemesh.controller.model.ServiceModel;
import dev.minemesh.controller.util.StringUtil;
import dev.minemesh.servicediscovery.common.model.ServiceState;
import graphql.com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Qualifier;
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
    private final RedisTemplate<String, String> stringRedisTemplate;
    private final RedisScript<String> counterScript;

    public ServiceRepositoryImpl(
            @Qualifier("script-id-parameter") RedisScript<String> counterScript,
            @Qualifier("template-service-model") RedisTemplate<String, ServiceModel> serviceRedisTemplate,
            @Qualifier("template-string") RedisTemplate<String, String> stringRedisTemplate
    ) {
        this.counterScript = counterScript;
        this.template = serviceRedisTemplate;
        this.valueOperations = serviceRedisTemplate.opsForValue();
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public <S extends ServiceModel> S save(S entity) {
        if (entity.getState() == null) {
            entity.setState(ServiceState.CREATED);
        }

        String id = entity.getId();
        if (id != null) {
            this.valueOperations.set(idToKey(id), entity);
            return entity;
        }

        String[] parameters = this.stringRedisTemplate.execute(this.counterScript, List.of()).split("[;.]");

        String generatedId = StringUtil.generateIdString(Long.parseLong(parameters[1]), Short.parseShort(parameters[0]));
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
    public List<ServiceModel> findAll() {
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
        return this.template.delete(
                this.template.keys("%s*".formatted(SERVICE_PREFIX))
        ) >= 1;
    }

    private static final String SERVICE_PREFIX = "service:";

    private static String idToKey(String id) {
        return SERVICE_PREFIX + id;
    }

    private static String keyToId(String key) {
        return key.substring(SERVICE_PREFIX.length());
    }

}
