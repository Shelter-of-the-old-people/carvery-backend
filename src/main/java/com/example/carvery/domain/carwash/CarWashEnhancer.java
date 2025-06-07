package com.example.carvery.domain.carwash;


import com.example.carvery.domain.carwash.DTO.GooglePlacesResponse;
import com.example.carvery.domain.carwash.DTO.PlaceDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@RequiredArgsConstructor
@Slf4j
public class CarWashEnhancer {

    @Value("${google.api.key}")
    private String googleApiKey;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private static final String PLACES_SEARCH_URL = "https://maps.googleapis.com/maps/api/place/textsearch/json";
    private static final String PHOTO_URL = "https://maps.googleapis.com/maps/api/place/photo";

    private static final String PHOTO_DETAIL_URL = "https://maps.googleapis.com/maps/api/place/details/json";

    @Cacheable(value = "place-details", key = "#businessName + '_' + #address", unless = "#result == null")
    public PlaceDetails getPlaceImageOnly(String businessName, String address) {
        try {
            String searchQuery = buildSearchQuery(businessName, address);
            log.info("Searching for: {}", searchQuery);

            String searchUrl = buildSearchUrl(searchQuery);
            log.info("Search URL: {}", searchUrl);
            ResponseEntity<String> searchResponse = restTemplate.getForEntity(searchUrl, String.class);
            log.info("Google Places API Response: {}", searchResponse.getBody());

            GooglePlacesResponse placesResponse = objectMapper.readValue(searchResponse.getBody(), GooglePlacesResponse.class);

            // results 배열이 비어있는지 확인
            if (placesResponse.getResults() == null || placesResponse.getResults().isEmpty()) {
                log.warn("No place found for query: {}", searchQuery);
                return null;
            }

            GooglePlacesResponse.PlaceResult result = placesResponse.getResults().get(0);

            // 사진이 있는지 확인
            if (result.getPhotos() == null || result.getPhotos().isEmpty()) {
                log.warn("No photos found for place: {}", result.getName());
                return null;
            }

            // 첫 번째 사진의 photo_reference로 이미지 URL 생성
            String photoReference = result.getPhotos().get(0).getPhoto_reference();
            String photoUrl = buildPhotoUrl(photoReference);

            return PlaceDetails.builder()
                    .placeId(result.getPlace_id())
                    .name(result.getName())
                    .photoReference(photoReference)
                    .photoUrl(photoUrl)
                    .build();

        } catch (Exception e) {
            log.error("Error fetching place image for businessName: {}, address: {}", businessName, address, e);
            return null;
        }
    }

    private String buildSearchQuery(String businessName, String address) {
        // "세차장 이름 + 도로명 주소" 형태로 검색 쿼리 생성
        return businessName + " " + address;
    }

    private String buildSearchUrl(String searchQuery) {
        String url = UriComponentsBuilder.fromUriString(PLACES_SEARCH_URL)
                .queryParam("query", searchQuery)
                .queryParam("key", googleApiKey)
                .queryParam("language", "ko")
                .build()
                .encode()
                .toUriString();

        return URLDecoder.decode(url, StandardCharsets.UTF_8);
    }

    private String buildPhotoUrl(String photoReference) {
        return UriComponentsBuilder.fromUriString(PHOTO_URL)
                .queryParam("photoreference", photoReference)
                .queryParam("maxwidth", "400")
                .queryParam("key", googleApiKey)
                .build()
                .encode()
                .toUriString();
    }

    private String buildPhotoDetailUrl(String placeId) {
        return UriComponentsBuilder.fromUriString(PHOTO_DETAIL_URL)
                .queryParam("place_id", placeId)
                .queryParam("fields", "photos")
                .queryParam("key", googleApiKey)
                .build()
                .encode()
                .toUriString();
    }


}
