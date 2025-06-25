package com.example.carvery.domain.carwash.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Getter
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum WashType {
    HandCarWash("손세차"),
    AutomaticCarWash("자동세차"),
    GeneralCarWash("일반세차"),
    SteamCarWash("스팀세차"),
    TransportCarWash("운수세차"),
    SelfCarWash("셀프세차"),
    Restoration("외형복원,덴트,도색"),
    CarPair("자동차정비,수리"),
    Tire("타이어,휠"),
    BusinessTripPair("출장정비")
    ;

    private final String korean;

    WashType(String korean) {
        this.korean = korean;
    }

    public static WashType fromCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            return null;
        }

        String trimmed = category.trim();

        // "세차"는 "일반세차"로 변환
        if ("세차".equals(trimmed)) {
            return GeneralCarWash;
        }

        for (WashType type : values()) {
            if (type.getKorean().equals(trimmed)) {
                return type;
            }
        }

        return null;
    }

    public static List<WashType> fromCategoryList(List<String> categories) {
        if (categories == null || categories.isEmpty()) {
            return new ArrayList<>();
        }

        return categories.stream()
                .map(WashType::fromCategory)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
    }
}