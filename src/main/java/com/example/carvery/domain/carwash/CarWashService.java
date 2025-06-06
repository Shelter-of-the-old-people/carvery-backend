package com.example.carvery.domain.carwash;

import com.example.carvery.domain.carwash.DTO.CarWashDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarWashService {

    private final CarWashRepository carWashRepository;
    private final CarWashUtils carWashUtils;  // 주입 추가

    public List<CarWashDto> findNearbyCarWash(double userLat, double userLng) {
        List<CarWashInfo> nearbyCarWash = carWashRepository.findWithinRadius(userLat, userLng, 10.0);

        return nearbyCarWash.stream()
                .map(carWash -> convertToDto(carWash, userLat, userLng))
                .collect(Collectors.toList());
    }

    private CarWashDto convertToDto(CarWashInfo carWash, double userLat, double userLng) {
        return CarWashDto.builder()
                .title(carWash.getCarWashName())
                .address(carWash.getCarWashAddress())
                .dist(String.format("%.1f",
                        carWashUtils.calculateDistance(userLat, userLng, carWash.getCarWashLatitude(), carWash.getCarWashLongitude())))
                .infos(carWashUtils.parseWashTypes(carWash.getCarWashType()))  // 수정된 부분
                .call(carWash != null ? carWash.getPhoneNumber() : "정보없음")
                .productImage(carWash.getImageUrl() != null ? carWash.getImageUrl() :
                        carWashUtils.getDefaultImage(carWash.getBusinessType()))
                .businessHours("정보없음")
                .build();
    }
}
