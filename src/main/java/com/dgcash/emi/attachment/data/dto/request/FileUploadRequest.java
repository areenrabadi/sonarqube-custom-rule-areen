package com.dgcash.emi.attachment.data.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class FileUploadRequest {

    private String fileName;
    private String fileType;
    private List<Field> fields;
}
