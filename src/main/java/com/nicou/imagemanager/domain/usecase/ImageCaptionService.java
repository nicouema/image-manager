package com.nicou.imagemanager.domain.usecase;

import java.io.IOException;

public interface ImageCaptionService {

    String getImageCaption(String file) throws IOException;
}
