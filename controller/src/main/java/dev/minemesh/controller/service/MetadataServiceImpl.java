package dev.minemesh.controller.service;

import dev.minemesh.controller.model.metadata.MetadataEntry;
import dev.minemesh.controller.model.metadata.MetadataIdentifier;
import dev.minemesh.controller.repository.MetadataRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MetadataServiceImpl implements MetadataService {

    private final MetadataRepository metadataRepository;

    public MetadataServiceImpl(MetadataRepository metadataRepository) {
        this.metadataRepository = metadataRepository;
    }

    @Override
    public Optional<String> findById(MetadataIdentifier metadataIdentifier) {
        return this.metadataRepository.findById(metadataIdentifier);
    }

    @Override
    public List<MetadataEntry> findAllById(String id, List<String> keys) {
        return this.metadataRepository.findAllById(id, keys);
    }

    @Override
    public List<MetadataEntry> findAll(String id) {
        return this.metadataRepository.findAll(id);
    }
}
