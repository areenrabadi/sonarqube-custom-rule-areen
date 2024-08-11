package com.dgcash.emi.attachment.busniess.service;

import com.dgcash.emi.attachment.busniess.exceptions.FileNotFoundOnFTPException;
import com.dgcash.emi.attachment.data.entities.Attachment;
import com.dgcash.emi.attachment.data.entities.Extension;
import com.dgcash.emi.attachment.data.entities.Type;
import com.dgcash.emi.attachment.data.repositories.AttachmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service for managing attachment entities, including creating and retrieving attachments.
 * This service interacts with the {@link AttachmentRepository} for database operations.
 *
 * <p>
 * Provides methods for saving new attachments and fetching existing ones based on file token and type.
 * </p>
 *
 * @author Ali Alkhatib
 * @since 2024-08-12
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;

    /**
     * Creates and saves a new attachment entity in the database.
     *
     * @param fileName  The name of the file.
     * @param filePath  The path where the file is stored.
     * @param fileSize  The size of the file in bytes.
     * @param token     The unique token associated with the file.
     * @param type      The type of the attachment.
     * @param extension The extension of the file.
     */
    public void create(String fileName, String filePath, Long fileSize, String token, Type type, Extension extension) {
        log.info("Creating new attachment: fileName={}, filePath={}, fileSize={}, token={}", fileName, filePath, fileSize, token);
        Attachment attachment = new Attachment();
        attachment.setFileName(fileName);
        attachment.setFilePath(filePath);
        attachment.setFileSize(fileSize);
        attachment.setToken(token);
        attachment.setType(type);
        attachment.setExtension(extension);
        attachmentRepository.save(attachment);
        log.info("Attachment created and saved with token: {}", token);
    }

    /**
     * Retrieves an attachment from the database based on the file token and type.
     *
     * @param fileToken The token of the file to retrieve.
     * @param fileType  The type of the file.
     * @return The {@link Attachment} entity matching the given token and type.
     * @throws FileNotFoundOnFTPException if no matching attachment is found.
     */
    public Attachment getAttachmentByTokenAndType(String fileToken, String fileType) {
        log.info("Fetching attachment with token: {} and type: {}", fileToken, fileType);
        return attachmentRepository.findByTokenAndType_Code(fileToken, fileType)
                .orElseThrow(() -> {
                    log.error("Attachment not found for token: {} and type: {}", fileToken, fileType);
                    return new FileNotFoundOnFTPException();
                });
    }
}
