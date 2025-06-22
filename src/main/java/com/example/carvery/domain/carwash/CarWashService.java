package com.example.carvery.domain.carwash;

import com.example.carvery.domain.carwash.DTO.CarWashDto;
import com.example.carvery.domain.carwash.DTO.PlaceDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarWashService {

    private final CarWashRepository carWashRepository;
    private final CarWashUtils carWashUtils;
    private final CarWashEnhancer carWashEnhancer;

    public List<CarWashDto> findNearbyCarWash(double userLat, double userLng, String address) {
        List<CarWashInfo> nearbyCarWash = carWashRepository.findNearestCarWash(userLat, userLng, 8.0, 15);

        return nearbyCarWash.stream()
                .map(carWash -> convertToDto(carWash, userLat, userLng))
                .collect(Collectors.toList());
    }

    private CarWashDto convertToDto(CarWashInfo carWash, double userLat, double userLng) {
        return CarWashDto.builder()
                .title(carWash.getCarWashName())
                .address(carWash.getCarWashAddress())
                .dist(carWashUtils.calculateDistance(userLat, userLng, carWash.getCarWashLatitude(), carWash.getCarWashLongitude()))

                .call(carWash.getCarWashCallNumber() != null ? carWash.getCarWashCallNumber() : "정보없음")
                .productImage(getImageUrl(carWash))  // 수정된 부분
                .businessHours("정보없음")
                .offDutyDay(carWash.getOffDutyDay())
                .build();
    }

    private String getImageUrl(CarWashInfo carWash) {
            PlaceDetails placeDetails = carWashEnhancer.getPlaceImageOnly(
                    carWash.getCarWashName(),
                    carWash.getCarWashAddress());

            if (placeDetails != null && placeDetails.getPhotoUrl() != null) {
                return placeDetails.getPhotoUrl();
            }

        // 3. 모든 방법이 실패하면 기본 이미지
        return carWashUtils.getDefaultImage();
    }
}