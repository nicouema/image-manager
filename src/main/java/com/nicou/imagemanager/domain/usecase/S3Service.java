package com.nicou.imagemanager.domain.usecase;

import com.nicou.imagemanager.ports.input.rs.response.ImageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public interface S3Service {

    CompletableFuture<ImageResponse> uploadImage(MultipartFile file);

    String getImageCaption(String fileUrl) throws IOException;
}
