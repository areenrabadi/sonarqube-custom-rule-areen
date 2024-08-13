package com.dgcash.emi.attachment.facade;

import com.dgcash.emi.attachment.busniess.exceptions.AttachmentNotFoundException;
import com.dgcash.emi.attachment.busniess.service.AttachmentService;
import com.dgcash.emi.attachment.data.dto.request.AttachmentRequest;
import com.dgcash.emi.attachment.data.dto.response.AttachmentResponse;
import com.dgcash.emi.attachment.data.entities.Attachment;
import com.dgcash.emi.attachment.data.mappers.AttachmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class AttachmentFacade {

    private final AttachmentService attachmentService;
    private final AttachmentMapper attachmentMapper;

    public AttachmentResponse create(Attachment attachment) {
        return attachmentMapper.toDto(attachmentService.create(attachment));
    }

    public AttachmentResponse getById(Long id) {
        return attachmentMapper.toDto(attachmentService.getById(id)
                .orElseThrow(AttachmentNotFoundException::new));
    }

    public List<AttachmentResponse> getAll() {
        return attachmentService.getAll()
                .stream()
                .map(attachmentMapper::toDto)
                .toList();
    }

    public AttachmentResponse getAttachmentByTokenAndType(String fileToken, String fileType) {
        return attachmentMapper.toDto(attachmentService.getAttachmentByTokenAndType(fileToken, fileType)
                .orElseThrow(AttachmentNotFoundException::new));
    }
}
