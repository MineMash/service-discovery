package dev.minemesh.controller.repository;

import dev.minemesh.controller.model.metadata.MetadataIdentifier;
import dev.minemesh.controller.model.metadata.MetadataEntry;
import org.reactivestreams.Publisher;
import org.springframework.data.repository.Repository;
import org.springframework.data.util.Pair;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Map;

public interface MetadataRepository extends Repository<String, MetadataIdentifier> {

    /**
     * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
     * entity instance completely.
     *
     * @param id must not be {@literal null}.
     * @param entity must not be {@literal null}.
     * @return {@link Mono} emitting the saved entity.
     * @throws IllegalArgumentException          in case the given {@literal entity} is {@literal null}.
     */
    Mono<String> save(MetadataIdentifier id, String entity);

    /**
     * Saves all given entities.
     *
     * @param serviceId must not be {@literal null}.
     * @param entities must not be {@literal null}.
     * @return {@link Flux} emitting the saved entities.
     * @throws IllegalArgumentException          in case the given {@link Iterable entities} or one of its entities is
     *                                           {@literal null}.
     */
    Flux<String> saveAll(String serviceId, Map<String, String> entities);

    /**
     * Saves all given entities.
     *
     * @param serviceId must not be {@literal null}.
     * @param entities must not be {@literal null}.
     * @return {@link Flux} emitting the saved entities.
     * @throws IllegalArgumentException          in case the given {@link Iterable entities} or one of its entities is
     *                                           {@literal null}.
     */
    Flux<String> saveAll(String serviceId, Collection<MetadataEntry> entities);

    /**
     * Saves all given entities.
     *
     * @param entityStream must not be {@literal null}.
     * @return {@link Flux} emitting the saved entities.
     * @throws IllegalArgumentException          in case the given {@link Publisher entityStream} is {@literal null}
     */
    Flux<String> saveAll(Publisher<Pair<MetadataIdentifier, String>> entityStream);

    /**
     * Retrieves an entity by its id.
     *
     * @param metadataIdentifier must not be {@literal null}.
     * @return {@link Mono} emitting the entity with the given id or {@link Mono#empty()} if none found.
     * @throws IllegalArgumentException in case the given {@literal id} is {@literal null}.
     */
    Mono<String> findByKey(MetadataIdentifier metadataIdentifier);

    /**
     * Retrieves an entity by its id supplied by a {@link Publisher}.
     *
     * @param id must not be {@literal null}. Uses the first emitted element to perform the find-query.
     * @return {@link Mono} emitting the entity with the given id or {@link Mono#empty()} if none found.
     * @throws IllegalArgumentException in case the given {@link Publisher id} is {@literal null}.
     */
    Mono<String> findByKey(Publisher<MetadataIdentifier> id);

    /**
     * Returns whether an entity with the given {@literal id} exists.
     *
     * @param metadataIdentifier must not be {@literal null}.
     * @return {@link Mono} emitting {@literal true} if an entity with the given id exists, {@literal false} otherwise.
     * @throws IllegalArgumentException in case the given {@literal id} is {@literal null}.
     */
    Mono<Boolean> existsByKey(MetadataIdentifier metadataIdentifier);

    /**
     * Returns whether an entity with the given id, supplied by a {@link Publisher}, exists. Uses the first emitted
     * element to perform the exists-query.
     *
     * @param id must not be {@literal null}.
     * @return {@link Mono} emitting {@literal true} if an entity with the given id exists, {@literal false} otherwise.
     * @throws IllegalArgumentException in case the given {@link Publisher id} is {@literal null}.
     */
    Mono<Boolean> existsByKey(Publisher<MetadataIdentifier> id);

    /**
     * Returns all instances of the type.
     *
     * @return {@link Flux} emitting all entities.
     * @param serviceId must not be {@literal null}.
     */
    Flux<MetadataEntry> findAll(String serviceId);

    /**
     * Returns all instances of the type.
     *
     * @return {@link Flux} emitting all entities.
     * @param serviceId must not be {@literal null}.
     */
    Flux<MetadataEntry> findAll(Publisher<String> serviceId);

    /**
     * Returns all instances of the type {@code T} with the given IDs.
     * <p>
     * If some or all ids are not found, no entities are returned for these IDs.
     * <p>
     * Note that the order of elements in the result is not guaranteed.
     *
     * @param serviceId           must not be {@literal null}.
     * @param metadataIdentifiers must not be {@literal null} nor contain any {@literal null} values.
     * @return {@link Flux} emitting the found entities. The size can be equal or less than the number of given
     * {@literal ids}.
     * @throws IllegalArgumentException in case the given {@link Iterable ids} or one of its items is {@literal null}.
     */
    Flux<MetadataEntry> findAllByKey(String serviceId, Iterable<String> metadataIdentifiers);

    /**
     * Returns all instances of the type {@code T} with the given IDs supplied by a {@link Publisher}.
     * <p>
     * If some or all ids are not found, no entities are returned for these IDs.
     * <p>
     * Note that the order of elements in the result is not guaranteed.
     *
     * @param idStream must not be {@literal null}.
     * @return {@link Flux} emitting the found entities.
     * @throws IllegalArgumentException in case the given {@link Publisher idStream} is {@literal null}.
     */
    Flux<MetadataEntry> findAllByKey(Publisher<MetadataIdentifier> idStream);

    /**
     * Returns the number of entities available.
     *
     * @param serviceId must not be {@literal null}.
     * @return {@link Mono} emitting the number of entities.
     */
    Mono<Long> count(String serviceId);

    /**
     * Returns the number of entities available.
     *
     * @param serviceId must not be {@literal null}.
     * @return {@link Mono} emitting the number of entities.
     */
    Mono<Long> count(Publisher<String> serviceId);

    /**
     * Deletes the entity with the given id.
     * <p>
     * If the entity is not found in the persistence store it is silently ignored.
     *
     * @param metadataIdentifier must not be {@literal null}.
     * @return {@link Mono} signaling when operation has completed.
     * @throws IllegalArgumentException in case the given {@literal id} is {@literal null}.
     */
    Mono<Void> deleteByKey(MetadataIdentifier metadataIdentifier);

    /**
     * Deletes the entity with the given id supplied by a {@link Publisher}.
     * <p>
     * If the entity is not found in the persistence store it is silently ignored.
     *
     * @param id must not be {@literal null}.
     * @return {@link Mono} signaling when operation has completed.
     * @throws IllegalArgumentException in case the given {@link Publisher id} is {@literal null}.
     */
    Mono<Void> deleteByKey(Publisher<MetadataIdentifier> id);

    /**
     * Deletes all instances of the type {@code T} with the given IDs.
     * <p>
     * Entities that aren't found in the persistence store are silently ignored.
     *
     * @param metadataIdentifiers must not be {@literal null}.
     * @return {@link Mono} signaling when operation has completed.
     * @throws IllegalArgumentException in case the given {@literal ids} or one of its elements is {@literal null}.
     *                                  {@literal null}.
     * @since 2.5
     */
    Mono<Void> deleteAllByKey(String serviceId, Iterable<? extends String> metadataIdentifiers);

    /**
     * Deletes all entities managed by the repository.
     *
     * @param serviceId must not be {@literal null}.
     * @return {@link Mono} signaling when operation has completed.
     */
    Mono<Void> deleteAll(String serviceId);

    /**
     * Deletes all entities managed by the repository.
     *
     * @param serviceId must not be {@literal null}.
     * @return {@link Mono} signaling when operation has completed.
     */
    Mono<Void> deleteAll(Publisher<String> serviceId);
}
