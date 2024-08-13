package com.dgcash.emi.attachment.data.mappers;

import com.dgcash.emi.attachment.data.dto.request.ExtensionRequest;
import com.dgcash.emi.attachment.data.dto.response.ExtensionResponse;
import com.dgcash.emi.attachment.data.entities.Extension;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ExtensionMapper {
    Extension toEntity(ExtensionRequest extensionRequest);

    ExtensionResponse toDto(Extension extension);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Extension partialUpdate(ExtensionRequest extensionRequest, @MappingTarget Extension extension);
}