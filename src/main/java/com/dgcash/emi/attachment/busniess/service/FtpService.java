package com.dgcash.emi.attachment.busniess.service;


import com.dgcash.emi.attachment.busniess.exceptions.UploadFileException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.integration.file.remote.session.Session;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE;


@Service
@RequiredArgsConstructor
@Slf4j
public class FtpService {

    private final SessionFactory<FTPFile> cachingSessionFactory;

    /**
     * Uploads a file to the FTP server, creating the necessary directory structure if it does not exist.
     *
     * @param file            The file to upload.
     * @param fileName        The name of the file to be stored.
     * @param directoryPattern The directory pattern where the file should be stored.
     * @throws UploadFileException if the file upload fails.
     */
    public void uploadFile(MultipartFile file, String fileName, String directoryPattern) {
        try {
            // Get an FTP client instance from the session factory
            FTPClient ftpClient = getFtpClient(cachingSessionFactory.getSession());

            // Create the directory structure if it does not exist
            createDirectoryIfNotExist(ftpClient, directoryPattern);

            // Attempt to store the file on the FTP server and validate if the file was successfully stored
            boolean storeFile = ftpClient.storeFile(directoryPattern + fileName, file.getInputStream());
            log.info("File stored: fileName={}, storeSuccessful={}", fileName, storeFile);
            validateStoreFile(storeFile);

            disconnectFtpClient(ftpClient);
        } catch (Exception e) {
            throw new UploadFileException();
        }
    }

    @SneakyThrows
    public boolean fileExists(String fileToken, String filePath) {
        FTPClient ftpClient = getFtpClient(cachingSessionFactory.getSession());
        FTPFile[] files = getListFiles(ftpClient, filePath + fileToken);
        disconnectFtpClient(ftpClient);
        return files.length > 0;
    }


    @SneakyThrows
    public byte[] viewFile(String fileName, String filePath) {
        FTPClient ftpClient = getFtpClient(cachingSessionFactory.getSession());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ftpClient.retrieveFile(filePath + fileName, outputStream);
        byte[] fileBytes = outputStream.toByteArray();
        disconnectFtpClient(ftpClient);
        return fileBytes;
    }

    @SneakyThrows
    private FTPClient getFtpClient(Session<FTPFile> session) {
        FTPClient ftpClient = (FTPClient) session.getClientInstance();
        ftpClient.setFileType(BINARY_FILE_TYPE);
        ftpClient.enterLocalPassiveMode();
        ftpClient.setRestartOffset(0L);
        return ftpClient;
    }

    private void createDirectoryIfNotExist(FTPClient ftpClient, String dirPath) throws IOException {
        String[] directories = dirPath.split("/");
        StringBuilder currentPath = new StringBuilder();

        for (String directory : directories) {
            if (!directory.isEmpty()) {
                currentPath.append("/").append(directory);
                if (!directoryExists(ftpClient, currentPath.toString())) {
                    ftpClient.makeDirectory(currentPath.toString());
                }
            }
        }
    }

    @SneakyThrows
    private boolean directoryExists(FTPClient ftpClient, String path) {
        FTPFile[] files = ftpClient.listFiles(path);
        return files != null && files.length > 0 && files[0].isDirectory();
    }

    private void validateStoreFile(boolean storeFile) {
        if (!storeFile) {
            throw new UploadFileException();
        }
    }

    private static void disconnectFtpClient(FTPClient ftpClient) throws IOException {
        ftpClient.logout();
        ftpClient.disconnect();
    }

    private static FTPFile[] getListFiles(FTPClient ftpClient, String currentPath) throws IOException {
        return ftpClient.listFiles(currentPath);
    }
}
