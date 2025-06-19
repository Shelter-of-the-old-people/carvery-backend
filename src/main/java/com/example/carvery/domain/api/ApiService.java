package com.example.carvery.domain.api;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ApiService {

    private final WebClient webClient;

    public ApiService() {
        this.webClient = WebClient.builder()
                .baseUrl("https://map.naver.com/p/api/search/allSearch")
                .defaultHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/137.0.0.0 Safari/537.36")
                .defaultHeader(HttpHeaders.REFERER, "https://map.naver.com/p/search/%EA%B5%AC%EB%AF%B8%EC%8B%9C%20%EC%84%B8%EC%B0%A8%EC%9E%A5?c=12.00,0,0,0,dh")
                .build();
    }

    public String getCarWashInfo(String query) {
        String responseBody = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("query", query)
                        .queryParam("type", "all")
                        .queryParam("searchCoord", "128.34430000000043;36.11959899999887")
                        .queryParam("boundary", "")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .block();  // 동기 호출

        System.out.println("응답 본문: " + responseBody);

        return responseBody;
    }
}
