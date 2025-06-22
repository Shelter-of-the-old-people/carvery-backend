package com.example.carvery.domain.api;

import com.example.carvery.domain.carwash.DTO.CarWashDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/carvery")
@CrossOrigin(origins = "http://localhost:5173")
public class ApiController {
    private final ApiService apiService;

    @GetMapping("/all")
    public ResponseEntity<List<NaverMapResDto>> getApi(@RequestParam("query") String query) throws Exception {
        List<NaverMapResDto> json = apiService.getNaverMapResList(query);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(json);
    }

    @GetMapping("/nearby")
    public ResponseEntity<List<CarWashDto>> getCarveryApi(
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam("query") String query) throws Exception {
        List<CarWashDto> json = apiService.getNaverMapResListToCarWashDto(lat, lng, query);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(json);
    }
}
