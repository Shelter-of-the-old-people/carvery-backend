package com.example.carvery.domain.naver;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class NaverShopSearch {

    private final String clientId = "ofwDyg7jTzuQhclxesGi";
    private final String clientSecret = "eSi52X0nCH";

    public String search(String query) {
        // 직접 쿼리스트링 붙이기
        String apiUrl = "https://openapi.naver.com/v1/search/shop.json?query=" + query + "&display=20";

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Naver-Client-Id", clientId);
        headers.add("X-Naver-Client-Secret", clientSecret);
        headers.add("User-Agent", "Mozilla/5.0"); // 일부 요청에서 필요한 경우
        headers.add("Referer", "https://www.naver.com/");

        HttpEntity<String> requestEntity = new HttpEntity<>("", headers);

        RestTemplate rest = new RestTemplate();
        ResponseEntity<String> responseEntity = rest.exchange(
                apiUrl,
                HttpMethod.GET,
                requestEntity,
                String.class
        );

        System.out.println("▶ 요청 URL: " + apiUrl);
        System.out.println("▶ 응답 코드: " + responseEntity.getStatusCodeValue());
        System.out.println("▶ 응답 내용: " + responseEntity.getBody());

        return responseEntity.getBody();
    }

    public List<ItemDto> fromJSONtoItems(String result) {
        JSONObject json = new JSONObject(result);
        JSONArray items = json.getJSONArray("items");

        List<ItemDto> itemDtoList = new ArrayList<>();
        for (int i = 0; i < items.length(); i++) {
            JSONObject itemJson = items.getJSONObject(i);
            itemDtoList.add(new ItemDto(itemJson));
        }

        return itemDtoList;
    }
}
