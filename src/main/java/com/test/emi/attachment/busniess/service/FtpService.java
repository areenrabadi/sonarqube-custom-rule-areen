package com.test.emi.attachment.busniess.service;


import com.test.emi.attachment.busniess.exceptions.UploadFileException;
import com.test.emi.attachment.data.dto.FileContent;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.integration.file.remote.session.Session;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE;


@Service
@RequiredArgsConstructor
@Slf4j
public class FtpService {

    private final SessionFactory<FTPFile> cachingSessionFactory;

    public void uploadFile(MultipartFile file, String fileName, String directoryPattern) {
        try {
            FTPClient ftpClient = getFtpClient(cachingSessionFactory.getSession());

            createDirectoryIfNotExist(ftpClient, directoryPattern);

            boolean storeFile = ftpClient.storeFile(directoryPattern + fileName, file.getInputStream());
            validateStoreFile(storeFile);

            disconnectFtpClient(ftpClient);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
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
                if (!ftpClient.changeWorkingDirectory(currentPath.toString())) {
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



    public FileContent viewFileContents(String fileName, String filePath) {
        Path tempFilePath;
        try {
            tempFilePath = Files.createTempFile("temp", ".pdf");
        } catch (IOException e) {
            throw new RuntimeException("Failed to create temporary file", e);
        }
        FTPClient ftpClient = getFtpClient(cachingSessionFactory.getSession());
        try (
             PDDocument document = loadPdfDocument(ftpClient, filePath, fileName, tempFilePath)) {
            return new FileContent( extractContentFromPdf(document));
        } catch (IOException e) {
            throw new RuntimeException("Failed to view file contents", e);
        } finally {
            // Clean up temporary file
            if (tempFilePath != null) {
                try {
                    Files.deleteIfExists(tempFilePath);
                } catch (IOException e) {
                    log.error("Failed to delete temporary file", e);
                }
            }
        }
    }

    private PDDocument loadPdfDocument(FTPClient ftpClient, String filePath, String fileName, Path tempFilePath) throws IOException {
        String remoteFile = filePath + fileName;
        try (InputStream inputStream = ftpClient.retrieveFileStream(remoteFile)) {
            Files.copy(inputStream, tempFilePath, StandardCopyOption.REPLACE_EXISTING);
            return PDDocument.load(tempFilePath.toFile());
        }
    }

    private String extractContentFromPdf(PDDocument document) {
        return IntStream.range(1, document.getNumberOfPages() + 1)
                .mapToObj(page -> fetchContent(page, document))
                .collect(Collectors.joining());
    }

    private String fetchContent(int page, PDDocument document) {
        try {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            pdfStripper.setStartPage(page);
            pdfStripper.setEndPage(page);
            String extractedText = pdfStripper.getText(document);
            String contentBody = extractContentBody(extractedText);
            return contentBody + "\n\n";
        } catch (IOException e) {
            throw new RuntimeException("Failed to fetch content", e);
        }
    }



    private String extractContentBody(String text) {
        return Arrays.stream(text.split("\\r?\\n"))
                .skip(14)
                .filter(line -> !line.trim().isEmpty())
                .filter(line -> !line.startsWith("Header") && !line.startsWith("Footer"))
                .collect(Collectors.joining("\n"));
    }



}
