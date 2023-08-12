package dev.minemesh.controller.service;

import dev.minemesh.controller.model.metadata.MetadataEntry;
import dev.minemesh.controller.model.metadata.MetadataIdentifier;

import java.util.List;
import java.util.Optional;

public interface MetadataService {


    Optional<String> findById(MetadataIdentifier metadataIdentifier);

    List<MetadataEntry> findAllById(String id, List<String> keys);

    List<MetadataEntry> findAll(String id);

    boolean update(String id, MetadataEntry entry);

}
