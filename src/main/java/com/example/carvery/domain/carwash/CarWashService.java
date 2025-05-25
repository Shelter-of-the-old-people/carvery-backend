package com.example.carvery.domain.carwash;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarWashService {

    public void resCarwashInfo(double lat, double lon) {
        BoundingBox boundingBox = calculateBoundingBox(lat, lon, 10.0);



    }

    private BoundingBox calculateBoundingBox(double latitude, double longitude, double radiusKm) {
        // 위도 범위 계산
        double deltaLat = radiusKm / 111.32;

        // 경도 범위 계산 (위도에 따른 조정 필요)
        double deltaLon = radiusKm / (111.32 * Math.cos(Math.toRadians(latitude)));

        double minLat = latitude - deltaLat;
        double maxLat = latitude + deltaLat;
        double minLon = longitude - deltaLon;
        double maxLon = longitude + deltaLon;

        return new BoundingBox(minLat, maxLat, minLon, maxLon);
    }
}
