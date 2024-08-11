package com.dgcash.emi.attachment.facade;

import com.dgcash.emi.attachment.busniess.exceptions.UploadFileException;
import com.dgcash.emi.attachment.busniess.service.AttachmentService;
import com.dgcash.emi.attachment.busniess.service.FtpService;
import com.dgcash.emi.attachment.busniess.service.SettingService;
import com.dgcash.emi.attachment.data.dto.request.FileUploadRequest;
import com.dgcash.emi.attachment.data.entities.Attachment;
import com.dgcash.emi.attachment.data.entities.Extension;
import com.dgcash.emi.attachment.data.entities.Setting;
import com.dgcash.emi.attachment.util.FileUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * Facade for handling FTP-related operations, including file upload, viewing, and existence checks.
 * This facade interacts with the {@link AttachmentService}, {@link SettingService}, and {@link FtpService}.
 *
 * <p>
 * The methods within this class handle file-related logic and delegate tasks to the respective services.
 * </p>
 *
 * @author Ali Alkhatib
 * @since 2024-08-12
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class FtpFacade {

    private final AttachmentService attachmentService;
    private final SettingService settingService;
    private final FtpService ftpService;

    /**
     * Uploads a file to the FTP server after validating its properties.
     *
     * @param multipartFile The file to be uploaded.
     * @param request       The file upload request as a JSON string.
     * @return The generated file token.
     * @throws UploadFileException if any error occurs during file upload.
     */
    public String uploadFile(final MultipartFile multipartFile, final String request) {
        try {
            log.info("Uploading file with name: {}", multipartFile.getOriginalFilename());
            FileUploadRequest fileUploadRequest = getFileUploadRequest(request);
            Setting settings = settingService.getSettingsByFileType(fileUploadRequest.getFileType());

            String fileExtension = FileUtil.getFileExtension(multipartFile.getInputStream());
            Long fileSize = FileUtil.getFileSize(multipartFile);

            // Validate the file against the settings
            validateFile(settings, fileExtension, fileSize);

            // Generate the file path and token
            String filePath = FileUtil.getFilePath(settings.getDirectoryPattern(), fileUploadRequest.getFields());
            String fileToken = FileUtil.generateFileToken(fileUploadRequest.getFileName(), fileSize, fileExtension);

            ftpService.uploadFile(multipartFile, fileToken, filePath);

            Extension extension = getExtension(settings, fileExtension);
            attachmentService.create(fileToken, filePath, fileSize, fileToken, settings.getType(), extension);
            log.info("File uploaded successfully with token: {}", fileToken);
            return fileToken;
        } catch (Exception e) {
            log.error("Error uploading file: request={}, fileName={}", request, multipartFile.getOriginalFilename(), e);
            throw new UploadFileException();
        }
    }

    /**
     * Retrieves and returns the file content from the FTP server based on the file token and type.
     *
     * @param fileToken The token of the file to retrieve.
     * @param fileType  The type of the file.
     * @return The file content as a byte array.
     */
    public byte[] viewFile(String fileToken, String fileType) {
        log.info("Viewing file with token: {}, type: {}", fileToken, fileType);
        Attachment attachment = attachmentService.getAttachmentByTokenAndType(fileToken, fileType);
        byte[] fileData = ftpService.viewFile(attachment.getFileName(), attachment.getFilePath());
        log.info("File with token: {} retrieved successfully", fileToken);
        return fileData;
    }

    /**
     * Checks if a file exists on the FTP server based on the file token and type.
     *
     * @param fileToken The token of the file to check.
     * @param fileType  The type of the file.
     * @return Boolean indicating whether the file exists.
     */
    public Boolean fileExists(String fileToken, String fileType) {
        log.info("Checking existence of file with token: {}, type: {}", fileToken, fileType);
        Attachment attachment = attachmentService.getAttachmentByTokenAndType(fileToken, fileType);
        boolean exists = ftpService.fileExists(attachment.getFileName(), attachment.getFilePath());
        log.info("File with token: {} exists: {}", fileToken, exists);
        return exists;
    }

    /**
     * Converts a JSON string to a {@link FileUploadRequest} object.
     *
     * @param request The JSON string representing the file upload request.
     * @return The corresponding {@link FileUploadRequest} object.
     * @throws JsonProcessingException if there is an error during JSON processing.
     */
    private FileUploadRequest getFileUploadRequest(String request) throws JsonProcessingException {
        log.debug("Parsing FileUploadRequest from JSON: {}", request);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(request, FileUploadRequest.class);
    }

    /**
     * Retrieves the corresponding {@link Extension} based on the file extension.
     *
     * @param settings      The settings for the file type.
     * @param fileExtension The file extension to match.
     * @return The matched {@link Extension} object.
     * @throws UploadFileException if the extension is not found.
     */
    private static Extension getExtension(Setting settings, String fileExtension) {
        log.debug("Retrieving extension for file extension: {}", fileExtension);
        return settings.getExtensions().stream()
                .filter(e -> e.getCode().equals(fileExtension))
                .findAny()
                .orElseThrow(UploadFileException::new);
    }

    /**
     * Validates the file's extension and size against the allowed settings.
     *
     * @param settings      The settings for the file type.
     * @param fileExtension The file's extension.
     * @param fileSize      The file's size.
     */
    private void validateFile(Setting settings, String fileExtension, Long fileSize) {
        log.debug("Validating file with extension: {} and size: {}", fileExtension, fileSize);
        FileUtil.validateFileExtension(settings.getExtensions(), fileExtension);
        FileUtil.validateFileSize(settings.getMaxAllowedSize(), fileSize);
    }
}
