package com.example.carvery.domain.carwash.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonFormat(shape = JsonFormat.Shape.STRING)
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