package com.example.carvery.domain.api;

import com.example.carvery.domain.carwash.CarWashUtils;
import com.example.carvery.domain.carwash.DTO.CarWashDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.carvery.domain.api.NaverMapResDto.parseFirstPlace;

@Service
@Slf4j
public class ApiService {

    private final RestTemplate restTemplate;
    private final String baseUrl = "https://map.naver.com/p/api/search/allSearch";

    public ApiService() {
        this.restTemplate = new RestTemplate();
    }

    public List<NaverMapResDto> getNaverMapResList(String query) throws Exception {
        // URL 생성
        String url = UriComponentsBuilder.fromUriString(baseUrl)
                .queryParam("query", query)
                .queryParam("type", "all")
                .queryParam("searchCoord", "128.34430000000043;36.11959899999887")
                .queryParam("boundary", "")
                .build()
                .toUriString();

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/137.0.0.0 Safari/537.36");
        headers.set(HttpHeaders.REFERER, "https://map.naver.com/p/search/%EA%B5%AC%EB%AF%B8%EC%8B%9C%20%EC%84%B8%EC%B0%A8%EC%9E%A5?c=12.00,0,0,0,dh");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            // API 호출
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            String responseBody = response.getBody();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(responseBody);

            JsonNode placeNode = root.path("result").path("place").path("list");
            String placeJson = objectMapper.writeValueAsString(placeNode);

            log.info("placeJson: {}", parseFirstPlace(placeJson).stream().toList());
            return parseFirstPlace(placeJson);

        } catch (Exception e) {
            e.printStackTrace();
            return parseFirstPlace("{}");
        }
    }

    public List<CarWashDto> getNaverMapResListToCarWashDto(double lat, double lon, String query) throws Exception {
        List<NaverMapResDto> naverRes = getNaverMapResList(query);

        return naverRes.stream()
                .map(naverMapResDto -> NaverMapResDto.NaverResConvertToDto(naverMapResDto, lat, lon))
                .sorted(Comparator.comparing(CarWashDto::getDist))
                .collect(Collectors.toList());
    }
}