package com.dgcash.emi.attachment.controller;


import com.dgcash.emi.attachment.busniess.service.FtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class FtpController {


    private final FtpService ftpService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        ftpService.uploadFile(file);
        return ResponseEntity.status(HttpStatus.OK).body("File uploaded successfully.");
    }


    @GetMapping("/download")
    public ResponseEntity<String> downloadFile(@RequestParam("fileName") String fileName) {
        ftpService.downloadFile(fileName);
        return ResponseEntity.status(HttpStatus.OK).body("File downloaded successfully.");
    }
}
