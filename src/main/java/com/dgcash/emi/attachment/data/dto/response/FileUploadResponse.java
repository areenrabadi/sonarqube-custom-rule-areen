package com.dgcash.emi.attachment.data.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileUploadResponse {

    private String fileToken;
}
