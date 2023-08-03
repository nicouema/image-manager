package com.nicou.imagemanager.domain.usecase.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.nicou.imagemanager.common.exception.UploadingFileException;
import com.nicou.imagemanager.configuration.S3Config;
import com.nicou.imagemanager.domain.model.Image;
import com.nicou.imagemanager.domain.repository.ImageRepository;
import com.nicou.imagemanager.domain.usecase.ImageCaptionService;
import com.nicou.imagemanager.domain.usecase.ImageDbService;
import com.nicou.imagemanager.domain.usecase.S3Service;
import com.nicou.imagemanager.ports.input.rs.mapper.ImageMapper;
import com.nicou.imagemanager.ports.input.rs.response.ImageResponse;
import com.nicou.imagemanager.ports.output.imagecaption.ImageCaptionIntegration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static com.nicou.imagemanager.common.util.ShaHashUtil.hashSha;


@Service
@RequiredArgsConstructor
@Slf4j
public class ImageStorageServiceImpl implements S3Service, ImageCaptionService, ImageDbService {

    private final AmazonS3 amazonS3;
    private final S3Config config;
    private final ImageCaptionIntegration imageCaptionIntegration;
    private final ImageRepository repository;
    private final ImageMapper mapper;

    @Override
    @Async
    public CompletableFuture<ImageResponse> uploadImage(MultipartFile image) {
        String fileUrl;
        try {
            File file = convertMultipartToFile(image);
            String fileName = generateFileName(file);
            String hash = getHashFromImage(image);
            if (repository.findById(hash).isPresent()) {
                return CompletableFuture.completedFuture(mapper.imageToImageResponse(repository.findById(hash).get()));
            }
            fileUrl = amazonS3.getUrl(config.getS3Bucket(), fileName).toString();
            uploadFileToS3Bucket(fileName, file);
            storeImage(fileUrl, file.getName(), hash);

        } catch (Exception e) {
            log.error("error uploading file", e);
            throw new UploadingFileException(e.getMessage());
        }
        return CompletableFuture.completedFuture(ImageResponse.builder()
                .uri(fileUrl)
                .fileName(image.getName())
                .build());
    }

    @Async
    protected void storeImage(String fileUrl, String fileName, String hash) throws IOException {
        String description = getImageCaption(fileUrl);
        saveImage(Image.builder()
                .hash(hash)
                .fileName(fileName)
                .description(description)
                .uri(fileUrl)
                .build());
    }

    private File convertMultipartToFile(MultipartFile multipartFile) throws IOException {
        File convertedFile = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(multipartFile.getBytes());
        fos.close();
        return convertedFile;
    }

    private String generateFileName(File multipartFile) {
        return LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME) + "-" + multipartFile.getName().replace(" ", "_");
    }

    @Async
    protected void uploadFileToS3Bucket(String filename, File file) {
        log.info(":::::: uploading image {} at {} ::::::", filename, LocalDateTime.now());
        var request = new PutObjectRequest(config.getS3Bucket(), filename, file)
                .withCannedAcl(CannedAccessControlList.PublicRead);
        amazonS3.putObject(request);
    }

    @Override
    public String getImageCaption(String fileUrl) throws IOException {
        log.info(":::::: Getting image caption of {} ::::::", fileUrl);
        return imageCaptionIntegration.getDescription(fileUrl);
    }

    @Override
    @Async
    public void saveImage(Image image) {
        log.info(":::::: Saving image {} ::::::", image.getUri());
        repository.save(image);
    }

    @Override
    public String getHashFromImage(MultipartFile imageFile) throws NoSuchAlgorithmException, IOException {
        byte[] imageBytes = imageFile.getBytes();
        return hashSha(imageBytes, "SHA-512");
    }
}
