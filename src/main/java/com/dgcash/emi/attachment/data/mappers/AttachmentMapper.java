package com.dgcash.emi.attachment.data.mappers;

import com.dgcash.emi.attachment.data.dto.request.AttachmentRequest;
import com.dgcash.emi.attachment.data.dto.response.AttachmentResponse;
import com.dgcash.emi.attachment.data.entities.Attachment;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AttachmentMapper {
    Attachment toEntity(AttachmentRequest attachmentRequest);

    AttachmentResponse toDto(Attachment attachment);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Attachment partialUpdate(AttachmentResponse attachmentResponse, @MappingTarget Attachment attachment);
}