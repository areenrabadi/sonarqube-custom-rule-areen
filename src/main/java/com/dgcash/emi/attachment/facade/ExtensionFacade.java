package com.dgcash.emi.attachment.facade;

import com.dgcash.emi.attachment.busniess.exceptions.AttachmentNotFoundException;
import com.dgcash.emi.attachment.busniess.exceptions.ExtensionNotFoundException;
import com.dgcash.emi.attachment.busniess.service.ExtensionService;
import com.dgcash.emi.attachment.data.dto.request.ExtensionRequest;
import com.dgcash.emi.attachment.data.dto.response.ExtensionResponse;
import com.dgcash.emi.attachment.data.entities.Extension;
import com.dgcash.emi.attachment.data.mappers.ExtensionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ExtensionFacade {

    private final ExtensionService extensionService;
    private final ExtensionMapper extensionMapper;

    public ExtensionResponse create(ExtensionRequest extensionRequest) {
        Extension extension = extensionMapper.toEntity(extensionRequest);
        return extensionMapper.toDto(extensionService.create(extension));
    }

    public ExtensionResponse getById(Long id) {
        Extension extension = extensionService.getById(id).orElseThrow(ExtensionNotFoundException::new);
        return extensionMapper.toDto(extension);
    }

    public List<ExtensionResponse> getAll() {
        return extensionService.getAll().stream().map(extensionMapper::toDto).toList();
    }

    public ExtensionResponse update(Long id, ExtensionRequest extensionRequest) {
        return extensionService.getById(id)
                .map(e -> extensionMapper.partialUpdate(extensionRequest, e))
                .map(extensionService::update)
                .map(extensionMapper::toDto)
                .orElseThrow(AttachmentNotFoundException::new);
    }

    public void delete(Long id) {
        extensionService.delete(id);
    }
}
