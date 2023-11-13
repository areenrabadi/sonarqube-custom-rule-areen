package com.dgcash.emi.attachment.busniess.service;


import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPSClient;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.file.remote.session.Session;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;

@Service
@RequiredArgsConstructor
public class FtpService {

    private final SessionFactory<FTPFile> cachingSessionFactory;

    @SneakyThrows
    public void uploadFile(MultipartFile file) {
        getFtpClient(cachingSessionFactory.getSession()).storeFile(getFileName(file), file.getInputStream());
    }

    @SneakyThrows
    public void downloadFile(String file) {
        getFtpClient(cachingSessionFactory.getSession()).retrieveFile(file, new FileOutputStream(file));
    }

    private String getFileName(MultipartFile file) {
        return file.getOriginalFilename();
    }

    @SneakyThrows
    private FTPClient getFtpClient(Session<FTPFile> session) {
        FTPClient ftpClient = (FTPClient) session.getClientInstance();
        ftpClient.setFileType(FTPSClient.BINARY_FILE_TYPE);
        ftpClient.enterLocalPassiveMode();
        return ftpClient;
    }
}
