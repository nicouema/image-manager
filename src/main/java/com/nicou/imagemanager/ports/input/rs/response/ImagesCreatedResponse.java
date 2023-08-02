package com.nicou.imagemanager.ports.input.rs.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ImagesCreatedResponse {

    private List<String> locations;

    public void addLocation(String aLocation) {
        if (locations == null){
            locations = new ArrayList<>();
        }
        locations.add(aLocation);
    }

}
