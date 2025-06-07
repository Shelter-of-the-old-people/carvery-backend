package com.example.carvery.domain.carwash;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@Table(name = "car_wash_info")
@NoArgsConstructor
@AllArgsConstructor
public class CarWashInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "사업장명")
    private String carWashName;

    @Column(name = "사업장업종명")
    private String carWashBusinessType;

    @Column(name = "세차유형")
    private String carWashType;

    @Column(name = "소재지도로명주소")
    private String carWashAddress;

    @Column(name = "WGS84위도")
    private Double carWashLatitude;

    @Column(name = "WGS84경도")
    private Double carWashLongitude;

    @Column(name = "세차장전화번호")
    private String carWashCallNumber;

    @Column(name = "휴무일")
    private String offDutyDay;
}
