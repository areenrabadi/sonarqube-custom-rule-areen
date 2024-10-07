package com.dgcash.emi.attachment.data.dto.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link com.dgcash.emi.attachment.data.entities.Attachment}
 */
@Builder
@Data
public class AttachmentResponse implements Serializable {
    Long id;
    String fileName;
    String filePath;
    Long fileSize;
    String token;
    TypeResponse type;
    ExtensionResponse extension;
    byte[] fileBytes;
}