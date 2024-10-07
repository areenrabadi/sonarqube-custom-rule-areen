package com.dgcash.emi.attachment.controller;

import com.dgcash.emi.attachment.data.dto.response.AttachmentResponse;
import com.dgcash.emi.attachment.data.dto.request.FileUploadResponse;
import com.dgcash.emi.attachment.facade.FtpFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
@Slf4j
public class FtpController {

    private final FtpFacade ftpFacade;


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
}
