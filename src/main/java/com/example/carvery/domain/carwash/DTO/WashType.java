package com.example.carvery.domain.carwash.DTO;

import lombok.Getter;

@Getter
public enum WashType {
    HandCarWash("손세차"),
    AutomaticCarWash("자동세차"),
    GeneralCarWash("일반세차"),
    SteamCarWash("스팀세차"),
    TransportCarWash("운송세차"),
    SelfCarWash("셀프세차");

    private final String korean;

    WashType(String korean) {
        this.korean = korean;
    }

}