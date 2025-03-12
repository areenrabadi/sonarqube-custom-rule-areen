package com.test.emi.attachment.util;

import com.test.emi.attachment.busniess.exceptions.FileSizeExceedsMaxLimitException;
import lombok.SneakyThrows;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.UUID;

import static java.util.Optional.ofNullable;

public class FileUtil {

    private FileUtil() {
    }

    public static String generateFileToken(String fileName, Long fileSize, String fileExtension) {
        return hashToUuid(String.join("", UUID.randomUUID().toString(), fileName, String.valueOf(fileSize), fileExtension));
    }

    public static void validateFileSize(Long maxAllowedSize, Long fileSize) {
        if (maxAllowedSize < fileSize) {
            throw new FileSizeExceedsMaxLimitException();
        }
    }

    private static String hashToUuid(String input) {
        return ofNullable(input)
                .map(FileUtil::toShaBytes)
                .map(ByteBuffer::wrap)
                .map(ByteBuffer::getLong)
                .map(b -> new UUID(b, b))
                .map(UUID::toString)
                .orElseThrow();
    }

    @SneakyThrows
    private static byte[] toShaBytes(String d) {
        return MessageDigest.getInstance("SHA-256")
                .digest(d.getBytes(StandardCharsets.UTF_8));
    }
}
