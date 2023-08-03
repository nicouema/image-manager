package com.nicou.imagemanager.ports.input.rs.mapper;

import com.nicou.imagemanager.domain.model.Image;
import com.nicou.imagemanager.ports.input.rs.response.ImageResponse;
import org.mapstruct.Mapper;

@Mapper
public interface ImageMapper {

    ImageResponse imageToImageResponse(Image image);

}
