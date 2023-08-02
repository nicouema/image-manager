package com.nicou.imagemanager.common.exception.handler;

import com.nicou.imagemanager.common.exception.ImageCaptionException;
import com.nicou.imagemanager.common.exception.UploadingFileException;
import com.nicou.imagemanager.common.exception.error.ErrorDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(UploadingFileException.class)
    public ResponseEntity<ErrorDetails> handleUploadingFileException(UploadingFileException e) {
        return ResponseEntity.internalServerError().body(ErrorDetails.builder()
                        .message(e.getMessage())
                .build());
    }

    @ExceptionHandler(ImageCaptionException.class)
    public ResponseEntity<ErrorDetails> handleImageCaptionException(ImageCaptionException e){
        return ResponseEntity.internalServerError().body(ErrorDetails.builder()
                        .message(e.getMessage())
                .build());
    }
}
