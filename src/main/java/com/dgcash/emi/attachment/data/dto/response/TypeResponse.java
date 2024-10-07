package com.dgcash.emi.attachment.data.dto.response;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.dgcash.emi.attachment.data.entities.Type}
 */
@Value
public class TypeResponse implements Serializable {
    Long id;
    String name;
    String code;
    String description;
}