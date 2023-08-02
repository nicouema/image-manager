package com.nicou.imagemanager.ports.input.rs.controller;

import com.nicou.imagemanager.domain.usecase.S3Service;
import com.nicou.imagemanager.ports.input.rs.api.ImageApi;
import com.nicou.imagemanager.ports.input.rs.response.ImagesCreatedResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
@Slf4j
public class ImageController implements ImageApi {

    private final S3Service s3Service;


    @PostMapping(value = "/upload", consumes = MULTIPART_FORM_DATA_VALUE)
    @Override
    public ResponseEntity<ImagesCreatedResponse> uploadImage(@RequestPart(value = "file") List<MultipartFile> images)
            throws ExecutionException, InterruptedException {

        ImagesCreatedResponse response = ImagesCreatedResponse.builder().build();

        for (MultipartFile image:images) {
            CompletableFuture<String> uri = s3Service.uploadImage(image);
            response.addLocation(uri.get());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
