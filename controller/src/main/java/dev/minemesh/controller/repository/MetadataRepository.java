package dev.minemesh.controller.repository;

import dev.minemesh.controller.model.metadata.IdentifiedMetadataEntry;
import dev.minemesh.controller.model.metadata.MetadataIdentifier;
import dev.minemesh.controller.model.metadata.MetadataEntry;
import org.springframework.data.repository.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MetadataRepository extends Repository<String, MetadataIdentifier> {

    /**
     * Saves a given value.
     *
     * @param identifier must not be {@literal null}.
     * @throws IllegalArgumentException          in case the given {@literal identifier} is {@literal null}.
     */
    void save(MetadataIdentifier identifier, String value);

    /**
     * Saves a given value.
     *
     * @param entry must not be {@literal null}.
     * @throws IllegalArgumentException          in case the given {@literal identifier} is {@literal null}.
     */
    void save(IdentifiedMetadataEntry entry);

    /**
     * Saves a given value.
     *
     * @param id must not be {@literal null}.
     * @param entry must not be {@literal null}.
     * @throws IllegalArgumentException          in case the given {@literal identifier} is {@literal null}.
     */
    void save(String id, MetadataEntry entry);

    /**
     * Saves all given entries.
     *
     * @param id      must not be {@literal null}.
     * @param entries must not be {@literal null} nor must it contain {@literal null}.
     * @throws IllegalArgumentException in case the given {@literal id}, {@link Iterable entries} or one of its entries is
     *                                  {@literal null}.
     */
    void saveAll(String id, Iterable<MetadataEntry> entries);

    /**
     * Retrieves an entry by its id.
     *
     * @param identifier must not be {@literal null}.
     * @return the entry with the given id or {@literal Optional#empty()} if none found.
     * @throws IllegalArgumentException if {@literal identifier} is {@literal null}.
     */
    Optional<String> findById(MetadataIdentifier identifier);

    /**
     * Returns whether an entry with the given id exists.
     *
     * @param identifier must not be {@literal null}.
     * @return {@literal true} if an entry with the given id exists, {@literal false} otherwise.
     * @throws IllegalArgumentException if {@literal identifier} is {@literal null}.
     */
    boolean existsById(MetadataIdentifier identifier);

    /**
     * Returns weather a metadata hash with the given id exists.
     *
     * @param id must not be {@literal null}.
     * @return {@literal true} if the metadata hash exists.
     * @throws IllegalArgumentException if {@literal identifier} is {@literal null}.
     */
    boolean exists(String id);

    /**
     * Returns all instances of the type.
     *
     * @param id must not be {@literal null}.
     * @return all entries.
     * @throws IllegalArgumentException if {@literal id} is {@literal null}.
     */
    List<MetadataEntry> findAll(String id);

    /**
     * Returns all instances of the type {@code T} with the given IDs.
     * <p>
     * If some or all ids are not found, no entries are returned for these IDs.
     * <p>
     *
     * @param metadataIdentifiers must not be {@literal null} nor contain any {@literal null} values.
     * @return guaranteed to be not {@literal null}. The size can be equal or less than the number of given
     * {@literal ids}.
     * @throws IllegalArgumentException in case the given {@link Iterable identifiers} or one of its items is {@literal null}.
     */
    List<IdentifiedMetadataEntry> findAllById(Collection<MetadataIdentifier> metadataIdentifiers);

    /**
     * Returns all instances of the type {@code T} with the given IDs.
     * <p>
     * If some or all ids are not found, no entries are returned for these IDs.
     * <p>
     *
     * @param id must not be {@literal null}.
     * @param keys must not be {@literal null} nor contain any {@literal null} values.
     * @return guaranteed to be not {@literal null}. The size can be equal or less than the number of given
     * {@literal ids}.
     * @throws IllegalArgumentException in case the given {@literal id}, {@link Iterable keys} or one of its items is {@literal null}.
     */
    List<MetadataEntry> findAllById(String id, List<String> keys);

    /**
     * Returns the number of entries available.
     *
     * @param id must not be {@literal null}.
     * @return the number of entries.
     */
    long count(String id);

    /**
     * Deletes the entry with the given id.
     * <p>
     * If the entry is not found in the persistence store it is silently ignored.
     *
     * @param identifier must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@literal id} is {@literal null}
     */
    void deleteById(MetadataIdentifier identifier);

    /**
     * Deletes all instances of the type {@code T} with the given IDs.
     * <p>
     * Entities that aren't found in the persistence store are silently ignored.
     *
     * @param metadataIdentifiers must not be {@literal null}. Must not contain {@literal null} elements.
     * @throws IllegalArgumentException in case the given {@literal ids} or one of its elements is {@literal null}.
     * @since 2.5
     */
    void deleteAllById(Iterable<? extends MetadataIdentifier> metadataIdentifiers);

    /**
     * Deletes all instances of the type {@code T} with the given IDs.
     * <p>
     * Entities that aren't found in the persistence store are silently ignored.
     *
     * @param id must not be {@literal null}.
     * @param keys must not be {@literal null}. Must not contain {@literal null} elements.
     * @throws IllegalArgumentException in case the given {@literal id}, {@literal keys} or one of its elements is {@literal null}.
     * @since 2.5
     */
    void deleteAllById(String id, Collection<String> keys);

    /**
     * Deletes all entries.
     *
     * @param id must not be {@literal null}.
     */
    void deleteAll(String id);
}
