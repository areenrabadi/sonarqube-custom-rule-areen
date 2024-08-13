package com.dgcash.emi.attachment.data.mappers;

import com.dgcash.emi.attachment.data.dto.request.SettingRequest;
import com.dgcash.emi.attachment.data.dto.response.SettingResponse;
import com.dgcash.emi.attachment.data.entities.Setting;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SettingMapper {
    Setting toEntity(SettingRequest settingRequest);

    SettingResponse toDto(Setting setting);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Setting partialUpdate(SettingRequest settingRequest, @MappingTarget Setting setting);
}