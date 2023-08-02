package com.nicou.imagemanager.ports.input.rs.api;


import com.nicou.imagemanager.ports.input.rs.response.ImagesCreatedResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface ImageApi {

    ResponseEntity<ImagesCreatedResponse> uploadImage(List<MultipartFile> images) throws ExecutionException, InterruptedException;

//    ResponseEntity<Resource> downloadImage()

}
