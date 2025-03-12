package com.test.emi.attachment.facade;

import com.test.emi.attachment.busniess.exceptions.AttachmentNotFoundException;
import com.test.emi.attachment.busniess.service.AttachmentService;
import com.test.emi.attachment.client.OtpClient;
import com.dgcash.emi.attachment.data.dto.request.CreateOtpRequest;
import com.dgcash.emi.attachment.data.dto.request.GenerateNewTokenRequest;
import com.test.emi.attachment.data.dto.response.AttachmentResponse;
import com.test.emi.attachment.data.entities.Attachment;
import com.test.emi.attachment.data.mappers.AttachmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class AttachmentFacade {

    private final AttachmentService attachmentService;
    private final AttachmentMapper attachmentMapper;
    private static final String SPLITER = "-";
    private final OtpClient otpClient;

    public AttachmentResponse create(Attachment attachment) {
        return attachmentMapper.toDto(attachmentService.create(attachment));
    }

    public AttachmentResponse getById(Long id) {
        return attachmentMapper.toDto(attachmentService.getById(id)
                .orElseThrow(AttachmentNotFoundException::new));
    }

    public List<AttachmentResponse> getAll() {
        return attachmentService.getAll()
                .stream()
                .map(attachmentMapper::toDto)
                .toList();
    }


    public AttachmentResponse getAttachmentByTokenAndType(String tokenValue, String fileType) {
        int lastIndex = tokenValue.lastIndexOf(SPLITER);
        if(!otpClient.verifyOtp("ATTACHMENT", tokenValue.substring(0, lastIndex), tokenValue.substring(lastIndex + 1))){
            throw new AttachmentNotFoundException();
        }
        return attachmentMapper.toDto(attachmentService.getAttachmentByTokenAndType(tokenValue.substring(0, lastIndex), fileType)
                .orElseThrow(AttachmentNotFoundException::new));
    }
    public String generateEncryptedToken(GenerateNewTokenRequest request) {
        return request.getEntityId() +
                SPLITER +getGenerateOtp(request.getRequesterId(), request.getEntityId())
                ;
    }
    private String getGenerateOtp(Long requesterId, String profileId) {
        return otpClient.generateOtp(getCreateOtpRequest(requesterId, profileId));
    }
    private CreateOtpRequest getCreateOtpRequest(Long requesterId, String profileId) {
        CreateOtpRequest createOtpRequest = new CreateOtpRequest();
        createOtpRequest.setRequesterId(requesterId);
        createOtpRequest.setEntityId(profileId);
        createOtpRequest.setOperationCode("ATTACHMENT");
        return createOtpRequest;
    }
}
