package com.example.carvery.domain.naver;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class NaverShopController {

    private final NaverShopSearch naverShopSearch;

    @GetMapping("/api/search")
    public List<ItemDto> search(@RequestParam String query) {
        String jsonResult = naverShopSearch.search(query);
        return naverShopSearch.fromJSONtoItems(jsonResult);
    }
}
