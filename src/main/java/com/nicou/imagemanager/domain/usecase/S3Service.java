package com.nicou.imagemanager.domain.usecase;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public interface S3Service {

    CompletableFuture<String> uploadImage(MultipartFile file);

    String getImageCaption(String fileUrl) throws IOException;
}
