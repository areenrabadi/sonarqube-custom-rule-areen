package com.dgcash.emi.attachment.facade;

import com.dgcash.emi.attachment.busniess.exceptions.SettingsNotFoundException;
import com.dgcash.emi.attachment.busniess.exceptions.UploadFileException;
import com.dgcash.emi.attachment.busniess.service.FtpService;
import com.dgcash.emi.attachment.busniess.service.SettingService;
import com.dgcash.emi.attachment.data.dto.request.AttachmentRequest;
import com.dgcash.emi.attachment.data.dto.request.FileUploadResponse;
import com.dgcash.emi.attachment.data.dto.response.AttachmentResponse;
import com.dgcash.emi.attachment.data.entities.Attachment;
import com.dgcash.emi.attachment.data.entities.Extension;
import com.dgcash.emi.attachment.data.entities.Setting;
import com.dgcash.emi.attachment.data.mappers.AttachmentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

import static com.dgcash.emi.attachment.util.FileUtil.generateFileToken;
import static com.dgcash.emi.attachment.util.FileUtil.validateFileSize;
import static com.dgcash.emi.attachment.util.StringUtil.replacePlaceHolders;

@Slf4j
@RequiredArgsConstructor
@Component
public class FtpFacade {

    private final AttachmentFacade attachmentFacade;
    private final AttachmentMapper attachmentMapper;
    private final SettingService settingService;
    private final FtpService ftpService;

    public String uploadFile(MultipartFile multipartFile, String fileName, String fileType) {
        return uploadFile(multipartFile, fileName, fileType, null);
    }

    public FileUploadResponse uploadFile(MultipartFile multipartFile, Map<String, String> parameters) {
        String fileToken = uploadFile(multipartFile, parameters.get("fileName"), parameters.get("fileType"), parameters);
        return FileUploadResponse.builder()
                .fileToken(fileToken)
                .build();
    }

    public String uploadFile(MultipartFile multipartFile, String fileName, String fileType,
                             Map<String, String> parameters) {
        try {
            String fileExtension = multipartFile.getContentType();
            Long fileSize = multipartFile.getSize();
            String fileToken = generateFileToken(fileName, fileSize, fileExtension);
            log.info("Uploading file {} to FTP", fileExtension);
            log.info("Uploading file {} to FTP", fileType);
            log.info("Uploading file {} to FTP", fileToken);


            Setting settings = settingService.getSettingsByFileType(fileType, fileExtension)
                    .orElseThrow(SettingsNotFoundException::new);

            String filePath = "/files/" + replacePlaceHolders(parameters, settings.getDirectoryPattern());

            validateFileSize(settings.getMaxAllowedSize(), fileSize);

            ftpService.uploadFile(multipartFile, fileName, filePath);

            attachmentFacade.create(getAttachment(fileToken, fileToken, filePath, fileSize, settings, fileExtension));
            return fileToken;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new UploadFileException();
        }
    }

    public byte[] viewFile(String fileToken, String fileType) {
        AttachmentResponse attachmentResponse = attachmentFacade.getAttachmentByTokenAndType(fileToken, fileType);
        return ftpService.viewFile(attachmentResponse.getFileName(), attachmentResponse.getFilePath());
    }

    public AttachmentResponse getAttachment(String fileToken, String fileType) {
        AttachmentResponse attachmentResponse = attachmentFacade.getAttachmentByTokenAndType(fileToken, fileType);
        byte[] fileBytes = ftpService.viewFile(attachmentResponse.getFileName(), attachmentResponse.getFilePath());
        attachmentResponse.setFileBytes(fileBytes);
        return attachmentResponse;
    }

    public Boolean fileExists(String fileToken, String fileType) {
        AttachmentResponse attachmentResponse = attachmentFacade.getAttachmentByTokenAndType(fileToken, fileType);
        return ftpService.fileExists(attachmentResponse.getFileName(), attachmentResponse.getFilePath());
    }

    private Attachment getAttachment(String fileName, String fileToken, String filePath, Long fileSize,
                                     Setting setting, String fileExtension) {

        Attachment attachment = new Attachment();
        attachment.setToken(fileToken);
        attachment.setFilePath(filePath);
        attachment.setFileName(fileName);
        attachment.setFileSize(fileSize);
        attachment.setType(setting.getType());
        attachment.setToken(fileToken);
        attachment.setExtension(getExtension(setting, fileExtension));
        return attachment;
    }

    private static Extension getExtension(Setting setting, String fileExtension) {
        return setting.getExtensions()
                .stream()
                .filter(e -> e.getCode().equals(fileExtension))
                .findFirst()
                .orElseThrow();
    }
}
