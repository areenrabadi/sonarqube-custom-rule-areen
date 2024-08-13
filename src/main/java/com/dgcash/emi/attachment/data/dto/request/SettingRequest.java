package com.dgcash.emi.attachment.data.dto.request;

import com.dgcash.emi.attachment.data.entities.Setting;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link Setting}
 */
@Value
public class SettingRequest implements Serializable {
    Long maxAllowedSize;
    String directoryPattern;
    TypeRequest type;
    List<ExtensionRequest> extensions;
}