package com.example.carvery.domain.carwash.DTO;

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
    private String productImage;
    private String title;
    private String dist;
    private String address;
    private List<WashType> infos;
    private String call;
    private String businessHours;
    private String offDutyDay;
}