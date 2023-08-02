package com.nicou.imagemanager.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class UploadingFileException extends RuntimeException{
    private final String message;
}
