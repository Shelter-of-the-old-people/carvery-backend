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
public class PlaceDetails {
    private String placeId;
    private String name;
    private String photoReference;
    private String photoUrl;
}
