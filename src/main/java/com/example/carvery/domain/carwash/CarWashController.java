package com.example.carvery.domain.carwash;

import com.example.carvery.domain.carwash.DTO.CarWashDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/carwash")
@RequiredArgsConstructor
public class CarWashController {

    private final CarWashService carWashService;

    // 현재 위치를 보내줌
    @GetMapping("/nearby")
    public ResponseEntity<List<CarWashDto>> getNearbyCarWash(
            @RequestParam double lat,
            @RequestParam double lng) {

        List<CarWashDto> result = carWashService.findNearbyCarWash(lat, lng);
        return ResponseEntity.ok(result);
    }
}
