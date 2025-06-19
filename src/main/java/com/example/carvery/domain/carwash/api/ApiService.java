package com.example.carvery.domain.api;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ApiService {
    public String getCarWashInfo(String query) {
        String baseUrl = "https://map.naver.com/p/api/search/allSearch";

        // 쿼리 파라미터 구성
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("query", query)
                .queryParam("type", "all")
                .queryParam("searchCoord", "128.34430000000043;36.11959899999887")
                .queryParam("boundary", "");

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/137.0.0.0 Safari/537.36");
        headers.set("Referer", "https://map.naver.com/p/search/%EA%B5%AC%EB%AF%B8%EC%8B%9C%20%EC%84%B8%EC%B0%A8%EC%9E%A5?c=12.00,0,0,0,dh");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.exchange(
                uriBuilder.toUriString(),
                HttpMethod.GET,
                entity,
                String.class
        );

        System.out.println("응답 코드: " + response.getStatusCode());
        System.out.println("응답 본문: " + response.getBody());

        return response.getBody();
    }
}
