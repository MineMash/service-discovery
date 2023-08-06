package dev.minemesh.controller.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface MetadataRepository extends ReactiveCrudRepository<String, String> { }
