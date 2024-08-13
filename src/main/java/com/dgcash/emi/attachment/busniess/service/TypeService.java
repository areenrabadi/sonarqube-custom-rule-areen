package com.dgcash.emi.attachment.busniess.service;

import com.dgcash.emi.attachment.busniess.exceptions.AttachmentNotFoundException;
import com.dgcash.emi.attachment.busniess.exceptions.TypeNotFoundException;
import com.dgcash.emi.attachment.data.entities.Type;
import com.dgcash.emi.attachment.data.repositories.TypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TypeService {

    private final TypeRepository typeRepository;

    public Type create(Type type) {
        return typeRepository.save(type);
    }

    public Optional<Type> getById(Long id) {
        return typeRepository.findById(id);
    }

    public List<Type> getAll() {
        return typeRepository.findAll();
    }

    public Type update(Type type) {
        Type typeEntity = typeRepository.findById(type.getId())
                .orElseThrow(AttachmentNotFoundException::new);

        typeEntity.setName(type.getName());
        typeEntity.setDescription(type.getDescription());
        typeEntity.setCode(type.getCode());

        return typeRepository.save(typeEntity);
    }

    public void delete(Long id) {
        Type type = typeRepository.findById(id).orElseThrow(TypeNotFoundException::new);
        type.setDeletedAt(Timestamp.from(Instant.now()));
        typeRepository.save(type);
    }
}
