package com.example.carvery.domain.carwash;

import com.example.carvery.domain.carwash.DTO.WashType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        return Optional.ofNullable(washType)
                .filter(type -> !type.trim().isEmpty())
                .map(type -> {
                    List<WashType> infos = new ArrayList<>();
                    String lowerWashType = type.toLowerCase();

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

                    return infos.isEmpty() ? List.of(WashType.GeneralCarWash) : infos;
                })
                .orElse(List.of(WashType.GeneralCarWash));
    }

    // 기본 이미지 설정
    public String getDefaultImage() {
        return "https://www.logoyogo.com/web/wp-content/uploads/edd/2021/03/logoyogo-1-122.jpg";
    }
}