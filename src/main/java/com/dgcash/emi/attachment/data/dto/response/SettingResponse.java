package com.dgcash.emi.attachment.data.dto.response;

import com.dgcash.emi.attachment.data.entities.Setting;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link Setting}
 */
@Value
public class SettingResponse implements Serializable {
    Long id;
    Long maxAllowedSize;
    String directoryPattern;
    TypeResponse type;
    List<ExtensionResponse> extensions;
}