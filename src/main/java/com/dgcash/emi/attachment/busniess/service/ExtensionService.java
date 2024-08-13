package com.dgcash.emi.attachment.busniess.service;

import com.dgcash.emi.attachment.busniess.exceptions.ExtensionNotFoundException;
import com.dgcash.emi.attachment.data.entities.Extension;
import com.dgcash.emi.attachment.data.repositories.ExtensionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ExtensionService {

    private final ExtensionRepository extensionRepository;

    public Extension create(Extension extension) {
        return extensionRepository.save(extension);
    }

    public Optional<Extension> getById(Long id) {
        return extensionRepository.findById(id);
    }

    public List<Extension> getAll() {
        return extensionRepository.findAll();
    }

    public Extension update(Extension extension) {
        Extension extensionEntity = extensionRepository.findById(extension.getId())
                .orElseThrow(ExtensionNotFoundException::new);
        extensionEntity.setName(extension.getName());
        extensionEntity.setDescription(extension.getDescription());
        extensionEntity.setCode(extension.getCode());
        return extensionRepository.save(extensionEntity);
    }

    public void delete(Long id) {
        Extension extensionEntity = extensionRepository.findById(id)
                .orElseThrow(ExtensionNotFoundException::new);
        extensionEntity.setDeletedAt(Timestamp.from(Instant.now()));
        extensionRepository.save(extensionEntity);
    }
}
