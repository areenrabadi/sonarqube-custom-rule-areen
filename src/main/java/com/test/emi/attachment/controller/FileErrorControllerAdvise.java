package com.test.emi.attachment.controller;

import com.dgcash.common.core.client.translation.TranslationClientHandler;
import com.dgcash.common.core.conroller.ErrorControllerAdvise;
import com.dgcash.common.core.data.dtos.translation.ErrorMessageDto;
import com.test.emi.attachment.busniess.exceptions.FileSizeExceedsMaxLimitException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class FileErrorControllerAdvise extends ErrorControllerAdvise {

    public FileErrorControllerAdvise(TranslationClientHandler translationClientHandler) {
        super(translationClientHandler);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorMessageDto> handleMaxSizeException(WebRequest request) {
        return handleBusinessException(new FileSizeExceedsMaxLimitException(), request);
    }
}
