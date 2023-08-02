package com.nicou.imagemanager.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Getter
public class ImageCaptionConfiguration {

    @Value("${hive.api.key}")
    private String key;

    @Value("${img.caption.api.url}")
    private String imageCaptionUrl;

    @Value("${azure.api.key}")
    private String azureKey;
}
