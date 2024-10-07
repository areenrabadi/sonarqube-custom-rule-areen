package com.dgcash.emi.attachment.data.dto.request;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link com.dgcash.emi.attachment.data.entities.Attachment}
 */
@Builder
@Data
public class AttachmentRequest implements Serializable {
    String fileName;
    String filePath;
    Long fileSize;
    String token;
    TypeRequest type;
    ExtensionRequest extension;
    byte[] fileBytes;
}