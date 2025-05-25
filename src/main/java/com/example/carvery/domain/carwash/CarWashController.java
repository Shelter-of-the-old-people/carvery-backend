package com.example.carvery.domain.carwash;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/carwash")
@RequiredArgsConstructor
public class CarWashController {

    private final CarWashService carWashService;

    // 현재 위치를 보내줌
    @GetMapping("/location")
    public ResponseEntity<?> responseCarWash(@RequestParam double lat, @RequestParam double lon) {

        carWashService.resCarwashInfo(lat, lon);

        return ResponseEntity.ok("test");
    }
}
