package com.example.carvery.domain.carwash;

import lombok.Getter;

@Getter
public class BoundingBox {
    // Getter
    private final double minLat;
    private final double maxLat;
    private final double minLon;
    private final double maxLon;

    public BoundingBox(double minLat, double maxLat, double minLon, double maxLon) {
        this.minLat = minLat;
        this.maxLat = maxLat;
        this.minLon = minLon;
        this.maxLon = maxLon;
    }

}
