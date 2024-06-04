package com.dgcash.emi.attachment.controller;


import com.dgcash.emi.attachment.busniess.service.FtpService;
import com.dgcash.emi.attachment.data.dto.FileUploadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class FtpController {

    private final FtpService ftpService;

    @PostMapping("/upload")
    public ResponseEntity<FileUploadResponse> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("fileName") String fileName, @RequestParam("fileType") String fileType) {
        return ResponseEntity.status(HttpStatus.OK).body(FileUploadResponse.builder().fileToken(ftpService.uploadFile(file, fileName, fileType)).build());
    }

    @GetMapping("/view")
    public ResponseEntity<byte[]> viewFile(@RequestParam("fileName") String fileName, @RequestParam("fileType") String fileType) {
        return ResponseEntity.status(HttpStatus.OK).body(ftpService.viewFile(fileName, fileType));
    }

    @GetMapping
    public ResponseEntity<Boolean> fileExists(@RequestParam("fileToken") String fileToken, @RequestParam("fileType") String fileType) {
        return ResponseEntity.status(HttpStatus.OK).body(ftpService.fileExists(fileToken, fileType));
    }
}
