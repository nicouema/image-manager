package com.nicou.imagemanager.ports.output.imagecaption;

import com.nicou.imagemanager.common.exception.ImageCaptionException;
import com.nicou.imagemanager.configuration.ImageCaptionConfiguration;
import com.nicou.imagemanager.ports.input.rs.request.ImageCaptionRequest;
import com.nicou.imagemanager.ports.input.rs.response.ImageCaptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ImageCaptionIntegration {

    private final RestTemplate restTemplate;
    private final ImageCaptionConfiguration imageCaptionConfig;

    public String getDescription(String imageUrl)  {
        try {
            // Prepare the request headers
            HttpHeaders headers = new HttpHeaders();
//            headers.set("Authorization", "Token " + imageCaptionConfig.getKey());
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Ocp-Apim-Subscription-Key", imageCaptionConfig.getAzureKey());

            // Prepare the request body (media file and data)
//            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
//
//            // Read the file content as binary data
//            byte[] fileContent = Files.readAllBytes(imageFile.toPath());
//
//            body.add("media", new ByteArrayResource(fileContent) {
//                @Override
//                public String getFilename() {
//                    return imageFile.getName();
//                }
//            });
//        body.add("callback_url", sampleUrl);
//        body.add("options", "{\"question\":\"how old is the kid?\"}");

            // Prepare the request entity (headers and body)
            //            body.add("uri", imageUrl);
            ImageCaptionRequest request = new ImageCaptionRequest(imageUrl);
            HttpEntity<ImageCaptionRequest> requestEntity = new HttpEntity<>(request, headers);

            UriComponentsBuilder uri = UriComponentsBuilder.fromHttpUrl(imageCaptionConfig.getImageCaptionUrl());
            uri.queryParam("api-version", "2023-04-01-preview");
            uri.queryParam("features", "caption");
            uri.queryParam("language", "en");
//            uri.queryParam("language", "en");

//            return restTemplate.getForObject(uri.toUriString(), String.class);
//             Send the POST request and get the response
            String stringUrl = uri.toUriString();
            ImageCaptionResponse response = Objects.requireNonNull(restTemplate.exchange(
                    stringUrl,
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<ImageCaptionResponse>() {
                    }).getBody());

            return response.getCaptionResult().getText();

        } catch (RestClientException e) {
            throw new ImageCaptionException(e.getMessage());
        }

    }

}
