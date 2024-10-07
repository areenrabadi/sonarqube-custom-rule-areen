package com.dgcash.emi.attachment.data.dto.response;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.dgcash.emi.attachment.data.entities.Extension}
 */
@Value
public class ExtensionResponse implements Serializable {
    Long id;
    String name;
    String code;
    String description;
}