package com.nicou.imagemanager.ports.input.rs.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ImageCaptionResponse {

    private CaptionResult captionResult;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class CaptionResult {
        private String text;
        private String confidence;
    }
}
