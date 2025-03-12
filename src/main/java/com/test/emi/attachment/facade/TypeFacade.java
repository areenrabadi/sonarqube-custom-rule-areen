package com.test.emi.attachment.facade;

import com.test.emi.attachment.busniess.exceptions.TypeNotFoundException;
import com.test.emi.attachment.busniess.service.TypeService;
import com.test.emi.attachment.data.dto.request.TypeRequest;
import com.test.emi.attachment.data.dto.response.TypeResponse;
import com.test.emi.attachment.data.mappers.TypeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class TypeFacade {

    private final TypeService typeService;
    private final TypeMapper typeMapper;

    public TypeResponse create(TypeRequest typeRequest) {
        return typeMapper.toDto(typeMapper.toEntity(typeRequest));
    }

    public TypeResponse getById(Long id) {
        return typeMapper.toDto(typeService.getById(id).orElseThrow(TypeNotFoundException::new));
    }

    public List<TypeResponse> getAll() {
        return typeService.getAll()
                .stream()
                .map(typeMapper::toDto)
                .toList();
    }

    public TypeResponse update(Long id, TypeRequest typeRequest) {
        return typeService.getById(id)
                .map(t -> typeMapper.partialUpdate(typeRequest, t))
                .map(typeService::update)
                .map(typeMapper::toDto)
                .orElseThrow(TypeNotFoundException::new);
    }

    public void delete(Long id) {
        typeService.delete(id);
    }
}
