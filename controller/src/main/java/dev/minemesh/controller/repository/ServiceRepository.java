package dev.minemesh.controller.repository;

import dev.minemesh.controller.model.ServiceModel;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface ServiceRepository extends Repository<ServiceModel, String> {

    /**
     * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
     * entity instance completely.
     *
     * @param entity must not be {@literal null}.
     * @return the saved entity; will never be {@literal null}.
     * @throws IllegalArgumentException          in case the given {@literal entity} is {@literal null}.
     */
    <S extends ServiceModel> S save(S entity);

    /**
     * Saves all given entities.
     *
     * @param entities must not be {@literal null} nor must it contain {@literal null}.
     * @return the saved entities; will never be {@literal null}. The returned {@literal Iterable} will have the same size
     * as the {@literal Iterable} passed as an argument.
     * @throws IllegalArgumentException          in case the given {@link Iterable entities} or one of its entities is
     *                                           {@literal null}.
     */
    <S extends ServiceModel> Iterable<S> saveAll(Iterable<S> entities);

    /**
     * Retrieves an entity by its id.
     *
     * @param s must not be {@literal null}.
     * @return the entity with the given id or {@literal Optional#empty()} if none found.
     * @throws IllegalArgumentException if {@literal id} is {@literal null}.
     */
    Optional<ServiceModel> findById(String s);

    /**
     * Returns whether an entity with the given id exists.
     *
     * @param s must not be {@literal null}.
     * @return {@literal true} if an entity with the given id exists, {@literal false} otherwise.
     * @throws IllegalArgumentException if {@literal id} is {@literal null}.
     */
    boolean existsById(String s);

    /**
     * Returns all instances of the type.
     *
     * @return all entities
     */
    List<ServiceModel> findAll();

    /**
     * Returns all instances of the type {@code T} with the given IDs.
     * <p>
     * If some or all ids are not found, no entities are returned for these IDs.
     * <p>
     * Note that the order of elements in the result is not guaranteed.
     *
     * @param strings must not be {@literal null} nor contain any {@literal null} values.
     * @return guaranteed to be not {@literal null}. The size can be equal or less than the number of given
     * {@literal ids}.
     * @throws IllegalArgumentException in case the given {@link Iterable ids} or one of its items is {@literal null}.
     */
    Iterable<ServiceModel> findAllById(Iterable<String> strings);

    /**
     * Returns the number of entities available.
     *
     * @return the number of entities.
     */
    long count();

    /**
     * Deletes the entity with the given id.
     * <p>
     * If the entity is not found in the persistence store it is silently ignored.
     *
     * @param s must not be {@literal null}.
     * @return true if changes happened in result of this method call.
     * @throws IllegalArgumentException in case the given {@literal id} is {@literal null}
     */
    boolean deleteById(String s);

    /**
     * Deletes a given entity.
     *
     * @param entity must not be {@literal null}.
     * @return true if changes happened in result of this method call.
     * @throws IllegalArgumentException          in case the given entity is {@literal null}.
     */
    boolean delete(ServiceModel entity);

    /**
     * Deletes all instances of the type {@code T} with the given IDs.
     * <p>
     * Entities that aren't found in the persistence store are silently ignored.
     *
     * @param strings must not be {@literal null}. Must not contain {@literal null} elements.
     * @return true if changes happened in result of this method call.
     * @throws IllegalArgumentException in case the given {@literal ids} or one of its elements is {@literal null}.
     * @since 2.5
     */
    boolean deleteAllById(Iterable<? extends String> strings);

    /**
     * Deletes the given entities.
     *
     * @param entities must not be {@literal null}. Must not contain {@literal null} elements.
     * @return true if changes happened in result of this method call.
     * @throws IllegalArgumentException          in case the given {@literal entities} or one of its entities is {@literal null}.
     */
    boolean deleteAll(Iterable<? extends ServiceModel> entities);

    /**
     * Deletes all entities managed by the repository.
     *
     * @return true if changes happened in result of this method call.
     */
    boolean deleteAll();

}
