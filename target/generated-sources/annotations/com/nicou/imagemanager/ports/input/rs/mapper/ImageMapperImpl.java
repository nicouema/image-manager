package com.nicou.imagemanager.ports.input.rs.mapper;

import com.nicou.imagemanager.domain.model.Image;
import com.nicou.imagemanager.ports.input.rs.response.ImageResponse;
import com.nicou.imagemanager.ports.input.rs.response.ImageResponse.ImageResponseBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.3 (Amazon.com Inc.)"
)
@Component
public class ImageMapperImpl implements ImageMapper {

    @Override
    public ImageResponse imageToImageResponse(Image image) {
        if ( image == null ) {
            return null;
        }

        ImageResponseBuilder imageResponse = ImageResponse.builder();

        imageResponse.fileName( image.getFileName() );
        imageResponse.uri( image.getUri() );

        return imageResponse.build();
    }
}
