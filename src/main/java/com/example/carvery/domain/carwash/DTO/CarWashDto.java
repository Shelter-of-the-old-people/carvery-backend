package com.example.carvery.domain.carwash.DTO;

import lombok.Builder;

import java.util.List;

@Builder
public class CarWashDto {
    private String productImage;
    private String title;
    private String dist;
    private String address;
    private List<WashType> infos;
    private String call;
    private String businessHours;
    private String offDutyDay;
}