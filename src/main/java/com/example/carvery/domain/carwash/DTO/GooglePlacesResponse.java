package com.example.carvery.domain.carwash.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GooglePlacesResponse {
    private List<PlaceResult> results;
    private String status;
    @JsonProperty("html_attributions")
    private List<String> htmlAttributions;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlaceResult {
        @JsonProperty("place_id")
        private String place_id;

        private String name;

        @JsonProperty("formatted_address")
        private String formatted_address;

        private List<Photo> photos;

        private Double rating;

        @JsonProperty("user_ratings_total")
        private Integer user_ratings_total;

        @JsonProperty("business_status")
        private String business_status;

        private List<String> types;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Photo {
        @JsonProperty("photo_reference")
        private String photo_reference;

        private Integer height;
        private Integer width;

        @JsonProperty("html_attributions")
        private List<String> html_attributions;
    }
}
