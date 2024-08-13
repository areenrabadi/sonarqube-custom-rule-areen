package com.dgcash.emi.attachment.data.dto.request;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.dgcash.emi.attachment.data.entities.Type}
 */
@Value
public class TypeRequest implements Serializable {
    String name;
    String code;
    String description;
}