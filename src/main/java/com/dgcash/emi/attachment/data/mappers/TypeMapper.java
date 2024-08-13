package com.dgcash.emi.attachment.data.mappers;

import com.dgcash.emi.attachment.data.dto.request.TypeRequest;
import com.dgcash.emi.attachment.data.dto.response.TypeResponse;
import com.dgcash.emi.attachment.data.entities.Type;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TypeMapper {
    Type toEntity(TypeRequest typeRequest);

    TypeResponse toDto(Type type);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Type partialUpdate(TypeRequest typeRequest, @MappingTarget Type type);
}