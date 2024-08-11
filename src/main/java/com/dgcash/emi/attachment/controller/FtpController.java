package com.dgcash.emi.attachment.controller;

import com.dgcash.emi.attachment.data.dto.response.FileUploadResponse;
import com.dgcash.emi.attachment.facade.FtpFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controller for handling file-related operations through FTP.
 * Provides endpoints for file upload, viewing, and existence checking.
 *
 * <p>
 * This controller uses the {@link FtpFacade} to delegate the FTP operations.
 * </p>
 *
 * @author Ali Alkhatib
 * @since 2024-08-12
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
@Slf4j
public class FtpController {

    private final FtpFacade ftpFacade;

    /**
     * Uploads a file to the FTP server.
     *
     * @param file              The file to upload.
     * @param fileUploadRequest Additional upload request parameters.
     * @return A ResponseEntity containing the file upload response, including the file token.
     */
    @PostMapping("/upload")
    public ResponseEntity<FileUploadResponse> uploadFile(@RequestParam MultipartFile file,
                                                         @RequestParam String fileUploadRequest) {
        log.info("Uploading file with name: {}", file.getOriginalFilename());
        String fileToken = ftpFacade.uploadFile(file, fileUploadRequest);
        FileUploadResponse fileUploadResponse = FileUploadResponse.builder().fileToken(fileToken).build();
        log.info("File uploaded successfully with token: {}", fileToken);
        return ResponseEntity.status(HttpStatus.OK).body(fileUploadResponse);
    }

    /**
     * Retrieves and displays a file from the FTP server.
     *
     * @param fileToken The token or name of the file to be viewed.
     * @param fileType The type of the file.
     * @return A ResponseEntity containing the file as a byte array.
     */
    @GetMapping("/view")
    public ResponseEntity<byte[]> viewFile(@RequestParam("fileToken") String fileToken,
                                           @RequestParam("fileType") String fileType) {
        log.info("Viewing file with token: {}, type: {}", fileToken, fileType);
        byte[] fileData = ftpFacade.viewFile(fileToken, fileType);
        log.info("File with token: {} retrieved successfully", fileToken);
        return ResponseEntity.status(HttpStatus.OK).body(fileData);
    }

    /**
     * Checks if a file exists on the FTP server.
     *
     * @param fileToken The token or name of the file to check.
     * @param fileType  The type of the file.
     * @return A ResponseEntity containing a boolean indicating whether the file exists.
     */
    @GetMapping
    public ResponseEntity<Boolean> fileExists(@RequestParam("fileToken") String fileToken,
                                              @RequestParam("fileType") String fileType) {
        log.info("Checking existence of file with token: {}, type: {}", fileToken, fileType);
        boolean exists = ftpFacade.fileExists(fileToken, fileType);
        log.info("File with token: {} exists: {}", fileToken, exists);
        return ResponseEntity.status(HttpStatus.OK).body(exists);
    }
}
