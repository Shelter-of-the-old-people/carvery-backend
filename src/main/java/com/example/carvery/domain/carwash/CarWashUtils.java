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

    // 개선된 세차 타입 파싱 - '+' 구분자와 enum 값 매칭 지원
    public List<WashType> parseWashTypes(String washType) {
        return Optional.ofNullable(washType)
                .map(String::trim)
                .filter(type -> !type.isEmpty())
                .map(this::parseWashTypeString)
                .orElse(List.of(WashType.GeneralCarWash));
    }

    private List<WashType> parseWashTypeString(String washType) {
        // '+' 구분자로 분리하여 각각 처리
        String[] washTypeParts = washType.split("\\+");

        List<WashType> result = new ArrayList<>();

        for (String part : washTypeParts) {
            String trimmedPart = part.trim();
            if (!trimmedPart.isEmpty()) {
                WashType matchedType = findMatchingWashType(trimmedPart);
                if (matchedType != null && !result.contains(matchedType)) {
                    result.add(matchedType);
                }
            }
        }

        return result.isEmpty() ? List.of(WashType.GeneralCarWash) : result;
    }

    private WashType findMatchingWashType(String washTypePart) {
        String lowerPart = washTypePart.toLowerCase();

        // 1. 먼저 enum의 korean 값과 정확히 일치하는지 확인
        for (WashType type : WashType.values()) {
            if (type.getKorean().equals(washTypePart)) {
                return type;
            }
        }

        // 2. 부분 문자열 매칭으로 찾기
        if (lowerPart.contains("손세차")) {
            return WashType.HandCarWash;
        }
        if (lowerPart.contains("자동세차") || lowerPart.contains("자동")) {
            return WashType.AutomaticCarWash;
        }
        if (lowerPart.contains("셀프세차") || lowerPart.contains("셀프")) {
            return WashType.SelfCarWash;
        }
        if (lowerPart.contains("스팀세차") || lowerPart.contains("스팀")) {
            return WashType.SteamCarWash;
        }
        if (lowerPart.contains("운수세차") || lowerPart.contains("운송세차") ||
                lowerPart.contains("운수") || lowerPart.contains("운송")) {
            return WashType.TransportCarWash;
        }
        if (lowerPart.contains("일반세차") || lowerPart.contains("일반")) {
            return WashType.GeneralCarWash;
        }

        return null; // 매칭되는 타입이 없음
    }


    // 기본 이미지 설정
    public String getDefaultImage() {
        return "https://www.logoyogo.com/web/wp-content/uploads/edd/2021/03/logoyogo-1-122.jpg";
    }
}