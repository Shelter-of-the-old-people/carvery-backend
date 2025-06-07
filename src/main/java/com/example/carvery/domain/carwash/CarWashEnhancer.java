package com.example.carvery.domain.carwash;


import com.example.carvery.domain.carwash.DTO.GooglePlacesResponse;
import com.example.carvery.domain.carwash.DTO.PlaceDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
@Slf4j
public class CarWashEnhancer {

    @Value("${google.api.key}")
    private String googleApiKey;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private static final String PLACES_SEARCH_URL = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json";
    private static final String PHOTO_URL = "https://maps.googleapis.com/maps/api/place/photo";

    @Cacheable(value = "place-details", key = "#searchQuery", unless = "#result == null")
    public PlaceDetails getPlaceImageOnly(String businessName, String address) {
        try {
            // 1. 검색 쿼리 생성: "세차장 이름 + 도로명 주소"
            String searchQuery = buildSearchQuery(businessName, address);
            log.info("Searching for: {}", searchQuery);

            // 2. Place Search API로 place_id 찾기
            String searchUrl = buildSearchUrl(searchQuery);
            String searchResponse = restTemplate.getForObject(searchUrl, String.class);

            GooglePlacesResponse placesResponse = objectMapper.readValue(searchResponse, GooglePlacesResponse.class);

            if (placesResponse.getCandidates().isEmpty()) {
                log.warn("No place found for query: {}", searchQuery);
                return null;
            }

            GooglePlacesResponse.PlaceCandidate candidate = placesResponse.getCandidates().get(0);

            // 3. 사진이 있는지 확인
            if (candidate.getPhotos() == null || candidate.getPhotos().isEmpty()) {
                log.warn("No photos found for place: {}", candidate.getName());
                return null;
            }

            // 4. 첫 번째 사진의 photo_reference로 이미지 URL 생성
            String photoReference = candidate.getPhotos().get(0).getPhoto_reference();
            String photoUrl = buildPhotoUrl(photoReference);

            return PlaceDetails.builder()
                    .placeId(candidate.getPlace_id())
                    .name(candidate.getName())
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
        return UriComponentsBuilder.fromUriString(PLACES_SEARCH_URL)
                .queryParam("input", searchQuery)
                .queryParam("inputtype", "textquery")
                .queryParam("fields", "place_id,name,photos")  // 이미지 관련 필드만
                .queryParam("key", googleApiKey)
                .queryParam("language", "ko")
                .build()
                .encode()
                .toUriString();
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

    // API 호출 제한을 위한 메서드
    public boolean canMakeApiCall() {
        // 간단한 호출 제한 로직 (실제로는 더 정교한 로직 필요)
        // 예: 하루 최대 100회 호출 등
        return true;
    }
}
