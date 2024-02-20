package com.dgcash.emi.attachment.busniess.service;


import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPSClient;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.file.remote.session.Session;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;


@Service
@RequiredArgsConstructor
public class FtpService {

    private final SessionFactory<FTPFile> cachingSessionFactory;
    private final PasswordEncoder passwordEncoder;

    @SneakyThrows
    public String uploadFile(MultipartFile file, String fileName, String filePath) {
        String fileToken = passwordEncoder.encode(fileName);
        getFtpClient(cachingSessionFactory.getSession()).storeFile(filePath + fileToken, file.getInputStream());
        return fileToken;
    }

    @SneakyThrows
    public void downloadFile(String file) {
        getFtpClient(cachingSessionFactory.getSession()).retrieveFile(file, new FileOutputStream(file));
    }

    @SneakyThrows
    public boolean fileExists(String fileToken, String path) {
        FTPClient ftpClient = getFtpClient(cachingSessionFactory.getSession());
        FTPFile[] files = ftpClient.listFiles(path + "/" + fileToken);
        ftpClient.logout();
        ftpClient.disconnect();
        return files.length > 0;
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
