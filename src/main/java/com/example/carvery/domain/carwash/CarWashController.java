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

    @GetMapping("/location")
    public ResponseEntity<?> responseCarWash(@RequestParam double latitude, @RequestParam double longitude) {
        carWashService.resCarwashInfo(latitude, longitude);

        return ResponseEntity.ok("test");
    }
}
