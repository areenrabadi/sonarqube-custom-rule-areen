package com.dgcash.emi.attachment.util;

import com.dgcash.emi.attachment.busniess.exceptions.FileExtensionNotAcceptedException;
import com.dgcash.emi.attachment.busniess.exceptions.FileSizeExceedsMaxLimitException;
import com.dgcash.emi.attachment.busniess.exceptions.InvalidFieldsNumberException;
import com.dgcash.emi.attachment.busniess.exceptions.UploadFileException;
import com.dgcash.emi.attachment.data.dto.request.Field;
import com.dgcash.emi.attachment.data.entities.Extension;
import org.apache.tika.Tika;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtil {

    private static final Tika tika = new Tika();

    private FileUtil() {
    }

    public static String getFileExtension(InputStream inputStream) throws IOException {
        return tika.detect(inputStream);
    }

    public static long getFileSize(MultipartFile multipartFile) {
        return multipartFile.getSize();
    }

    public static String generateFileToken(String fileName, Long fileSize, String fileExtension)
            throws NoSuchAlgorithmException {

        String combinedString = fileName + fileSize + fileExtension;
        return hashToUuid(combinedString);
    }

    public static void validateFileExtension(List<Extension> extensions, String fileExtension) {
        boolean isValidFileExtension = extensions.stream().anyMatch(e -> e.getCode().equals(fileExtension));
        if (!isValidFileExtension) {
            throw new FileExtensionNotAcceptedException();
        }
    }

    public static void validateFileSize(Long maxAllowedSize, Long fileSize) {
        if (maxAllowedSize < fileSize) {
            throw new FileSizeExceedsMaxLimitException();
        }
    }

    public static String getFilePath(String directoryPattern, List<Field> fields) {
        List<String> extractedFields = extractFields(directoryPattern);
        validateFields(fields, extractedFields);
        return StringUtil.replacePlaceHolders(fields, directoryPattern);
    }

    private static List<String> extractFields(String directoryPattern) {
        String regex = "\\{\\?([^}]+)}";
        Pattern compiledPattern = Pattern.compile(regex);
        Matcher matcher = compiledPattern.matcher(directoryPattern);
        return matcher.results().map(a -> a.group(1)).toList();
    }

    private static void validateFields(List<Field> fields, List<String> extractedFields) {
        if (fields.size() != extractedFields.size()) {
            throw new InvalidFieldsNumberException();
        }
    }

    private static String hashToUuid(String input) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
        ByteBuffer buffer = ByteBuffer.wrap(hashBytes);
        long mostSigBits = buffer.getLong();
        long leastSigBits = buffer.getLong();
        UUID uuid = new UUID(mostSigBits, leastSigBits);
        return uuid.toString();
    }
}
