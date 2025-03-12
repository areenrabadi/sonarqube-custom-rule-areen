package com.test.emi.attachment.controller;

import com.test.emi.attachment.data.dto.FileContent;
import com.dgcash.emi.attachment.data.dto.request.GenerateNewTokenRequest;
import com.test.emi.attachment.data.dto.response.AttachmentResponse;
import com.test.emi.attachment.data.dto.request.FileUploadResponse;
import com.test.emi.attachment.facade.AttachmentFacade;
import com.test.emi.attachment.facade.FtpFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
@Slf4j
public class FtpController {

    private final FtpFacade ftpFacade;
    private final AttachmentFacade attachmentFacade;


    @PostMapping("/upload")
    public ResponseEntity<FileUploadResponse> uploadFile(@RequestParam("file") MultipartFile file,
                                                         @RequestParam("fileName") String fileName,
                                                         @RequestParam("fileType") String fileType) {
        return ResponseEntity.status(HttpStatus.OK).body(FileUploadResponse.builder().fileToken(ftpFacade.uploadFile(file, fileName, fileType)).build());
    }

    @GetMapping("/view")
    public ResponseEntity<byte[]> viewFile(@RequestParam("fileToken") String fileToken,
                                           @RequestParam("fileType") String fileType) {
        return ResponseEntity.status(HttpStatus.OK).body(ftpFacade.viewFile(fileToken, fileType));
    }

    @GetMapping
    public ResponseEntity<Boolean> fileExists(@RequestParam("fileToken") String fileToken,
                                              @RequestParam("fileType") String fileType) {
        boolean exists = ftpFacade.fileExists(fileToken, fileType);
        return ResponseEntity.status(HttpStatus.OK).body(exists);
    }

    @GetMapping("/view/contents")
    public ResponseEntity<FileContent> viewFileContents(@RequestParam("fileToken") String fileToken, @RequestParam("fileType") String fileType) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(ftpFacade.viewFileContents(fileToken, fileType));
    }

    @PostMapping("/attachment/upload")
    public ResponseEntity<FileUploadResponse> uploadFile(@RequestParam MultipartFile file,
                                                         @RequestParam MultiValueMap<String, String> multiValueMap) {
        return ResponseEntity.status(HttpStatus.OK).body(ftpFacade.uploadFile(file, multiValueMap.toSingleValueMap()));
    }

    @GetMapping("/attachment")
    public ResponseEntity<AttachmentResponse> getFileAttachment(@RequestParam("fileToken") String fileToken,
                                                                @RequestParam("fileType") String fileType) {
        return ResponseEntity.status(HttpStatus.OK).body(ftpFacade.getAttachment(fileToken, fileType));
    }

    @PostMapping("/attachment/token")
    public ResponseEntity<String> generateTokenForFile(@RequestBody GenerateNewTokenRequest request) {
        return ResponseEntity.ok(attachmentFacade.generateEncryptedToken(request));
    }
}
