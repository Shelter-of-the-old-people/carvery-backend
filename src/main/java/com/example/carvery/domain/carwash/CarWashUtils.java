package com.example.carvery.domain.carwash;

import com.example.carvery.domain.carwash.DTO.WashType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CarWashUtils {

    // 거리 계산 (Haversine formula)
    public double calculateDistance(double lat1, double lng1, double lat2, double lng2) {
        final int R = 6371; // 지구 반지름 (km)

        double latDistance = Math.toRadians(lat2 - lat1);
        double lngDistance = Math.toRadians(lng2 - lng1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }

    // 세차 타입 파싱
    public List<WashType> parseWashTypes(String washType) {
        List<WashType> infos = new ArrayList<>();

        String lowerWashType = washType.toLowerCase();

        if (lowerWashType.contains("자동세차") || lowerWashType.contains("자동")) {
            infos.add(WashType.AutomaticCarWash);
        }
        if (lowerWashType.contains("손세차")) {
            infos.add(WashType.HandCarWash);
        }
        if (lowerWashType.contains("셀프세차") || lowerWashType.contains("셀프")) {
            infos.add(WashType.SelfCarWash);
        }
        if (lowerWashType.contains("스팀세차") || lowerWashType.contains("스팀")) {
            infos.add(WashType.SteamCarWash);
        }
        if (lowerWashType.contains("운송세차") || lowerWashType.contains("운송")) {
            infos.add(WashType.TransportCarWash);
        }
        if (lowerWashType.contains("일반세차") || lowerWashType.contains("일반")) {
            infos.add(WashType.GeneralCarWash);
        }

        // 기본값: 매칭되는 타입이 없으면 일반세차로 설정
        if (infos.isEmpty()) {
            infos.add(WashType.GeneralCarWash);
        }

        return infos;
    }

    // 기본 이미지 설정
    public String getDefaultImage(String businessType) {
        return businessType.contains("주유소") ?
                "https://kixxman.com/files/attach/images/140/820/005/7c05df79f4485a77bfa89bf69f347f11.jpg" :
                "/images/carwash-default.png";
    }
}