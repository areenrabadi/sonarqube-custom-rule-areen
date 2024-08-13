package com.dgcash.emi.attachment.busniess.service;

import com.dgcash.emi.attachment.busniess.exceptions.AttachmentNotFoundException;
import com.dgcash.emi.attachment.data.entities.Attachment;
import com.dgcash.emi.attachment.data.repositories.AttachmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;

    public Attachment create(Attachment attachment) {
        return attachmentRepository.save(attachment);
    }

    public Optional<Attachment> getById(Long id) {
        return attachmentRepository.findById(id);
    }

    public List<Attachment> getAll() {
        return attachmentRepository.findAll();
    }

    public Optional<Attachment> getAttachmentByTokenAndType(String fileToken, String fileType) {
        return attachmentRepository.findByTokenAndType_Code(fileToken, fileType);
    }
}
