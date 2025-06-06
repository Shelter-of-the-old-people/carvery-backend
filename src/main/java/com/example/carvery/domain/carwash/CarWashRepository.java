package com.example.carvery.domain.carwash;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarWashRepository extends JpaRepository<CarWashInfo, Integer> {

    @Query(value = """
        SELECT *, 
        (6371 * acos(cos(radians(?1)) * cos(radians(WGS84위도)) * 
        cos(radians(WGS84경도) - radians(?2)) + sin(radians(?1)) * 
        sin(radians(WGS84위도)))) AS distance 
        FROM car_wash_info 
        WHERE (6371 * acos(cos(radians(?1)) * cos(radians(WGS84위도)) * 
        cos(radians(WGS84경도) - radians(?2)) + sin(radians(?1)) * 
        sin(radians(WGS84위도)))) <= ?3
        ORDER BY distance
        """, nativeQuery = true)
    List<CarWashInfo> findWithinRadius(double lat, double lng, double radiusKm);
}
