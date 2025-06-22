package com.example.carvery.domain.carwash.DTO;

import com.example.carvery.domain.api.NaverMapResDto;
import com.example.carvery.domain.carwash.CarWashInfo;
import com.example.carvery.domain.carwash.CarWashUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarWashDto {
    private String id;
    private String productImage;
    private String title;
    private Double dist;
    private String address;
    private List<String> infos;
    private String call;
    private String businessHours;
    private String offDutyDay;
    private double lat;
    private double lon;
}