package com.example.carvery.domain.naver;

import lombok.Getter;
import org.json.JSONObject;

@Getter
public class ItemDto {
    private String title;
    private String brand;
    private String mallName;
    private String link;
    private String image;
    private int lprice;

    public ItemDto(JSONObject itemJson) {
        this.title = itemJson.getString("title");
        this.brand = itemJson.getString("brand");
        this.mallName = itemJson.getString("mallName");
        this.link = itemJson.getString("link");
        this.image = itemJson.getString("image");
        this.lprice = itemJson.getInt("lprice");
    }
}
