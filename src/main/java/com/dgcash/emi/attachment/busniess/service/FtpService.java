package com.dgcash.emi.attachment.busniess.service;


import com.dgcash.emi.attachment.busniess.exceptions.FileNotFoundOnFTPException;
import com.dgcash.emi.attachment.busniess.exceptions.UploadFileException;
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

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;


@Service
@RequiredArgsConstructor
public class FtpService {

    private final SessionFactory<FTPFile> cachingSessionFactory;
    private final PasswordEncoder passwordEncoder;

    @SneakyThrows
    public String uploadFile(MultipartFile file, String fileName, String filePath) {
        String fileToken = passwordEncoder.encode(fileName);
        try {
            getFtpClient(cachingSessionFactory.getSession()).storeFile(filePath + fileToken, file.getInputStream());
        } catch (Exception e) {
            throw new UploadFileException();
        }

        return fileToken;
    }

    @SneakyThrows
    public void downloadFile(String file) {
        getFtpClient(cachingSessionFactory.getSession()).retrieveFile(file, new FileOutputStream(file));
    }

    @SneakyThrows
    public boolean fileExists(String fileToken, String path) {
        FTPClient ftpClient = getFtpClient(cachingSessionFactory.getSession());
        FTPFile[] files = getListFiles(ftpClient, path + "/" + fileToken);
        disconnectFtpClient(ftpClient);
        return files.length > 0;
    }


    @SneakyThrows
    public byte[] viewFile(String fileName) {
        FTPClient ftpClient = getFtpClient(cachingSessionFactory.getSession());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ftpClient.retrieveFile(findFilePath(ftpClient, fileName, "/") + fileName, outputStream);
        byte[] fileBytes = outputStream.toByteArray();
        disconnectFtpClient(ftpClient);
        return fileBytes;
    }

    private static void disconnectFtpClient(FTPClient ftpClient) throws IOException {
        ftpClient.logout();
        ftpClient.disconnect();
    }

    @SneakyThrows
    private String findFilePath(FTPClient ftpClient, String fileName, String currentPath) {
        FTPFile[] files = getListFiles(ftpClient, currentPath);
        if (files != null) {
            return Optional.ofNullable(getFilePath(ftpClient, fileName, currentPath, files).orElse(getFilePathInCurrentDir(fileName, currentPath, files).orElse(null))).orElseThrow(FileNotFoundOnFTPException::new);
        }
        return null;
    }

    private static Optional<String> getFilePathInCurrentDir(String fileName, String currentPath, FTPFile[] files) {
        return Stream.of(files).filter(file -> checkIfFile(fileName, file)).map(file -> currentPath).findFirst();
    }

    private Optional<String> getFilePath(FTPClient ftpClient, String fileName, String currentPath, FTPFile[] files) {
        return Stream.of(files).filter(FtpService::checkIfDirectory).map(file -> currentPath + file.getName() + "/").map(newPath -> findFilePath(ftpClient, fileName, newPath)).findFirst();
    }

    private static boolean checkIfFile(String fileName, FTPFile file) {
        return file.isFile() && file.getName().equals(fileName);
    }

    private static boolean checkIfDirectory(FTPFile file) {
        return file.isDirectory() && !file.getName().equals(".") && !file.getName().equals("..");
    }

    private static FTPFile[] getListFiles(FTPClient ftpClient, String currentPath) throws IOException {
        return ftpClient.listFiles(currentPath);
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
