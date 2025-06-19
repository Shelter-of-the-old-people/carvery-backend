package com.example.carvery.domain.api;

import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApiController {
    private final ApiService apiService;

    @GetMapping("/test")
    public ResponseEntity<String> getApi(@RequestParam("query") String query) {

        String json = apiService.getCarWashInfo(query);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON) // <- 이게 중요!
                .body(json);
    }
}
