package com.nicou.imagemanager.domain.usecase;

import com.nicou.imagemanager.domain.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public interface ImageDbService {

    void saveImage(Image image);

    String getHashFromImage(MultipartFile imageFile) throws NoSuchAlgorithmException, IOException;

}
