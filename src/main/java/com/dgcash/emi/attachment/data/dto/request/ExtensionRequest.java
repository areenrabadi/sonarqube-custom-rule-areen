package com.dgcash.emi.attachment.data.dto.request;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.dgcash.emi.attachment.data.entities.Extension}
 */
@Value
public class ExtensionRequest implements Serializable {
    String name;
    String code;
    String description;
}