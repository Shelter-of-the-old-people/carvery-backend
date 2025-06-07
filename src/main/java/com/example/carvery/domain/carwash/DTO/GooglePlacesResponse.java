package com.example.carvery.domain.carwash.DTO;

import lombok.Data;

import java.util.List;

@Data
public class GooglePlacesResponse {
    private List<PlaceCandidate> candidates;
    private String status;

    @Data
    public static class PlaceCandidate {
        private String place_id;
        private String name;
        private List<Photo> photos;
    }

    @Data
    public static class Photo {
        private String photo_reference;
        private Integer width;
        private Integer height;
    }
}
