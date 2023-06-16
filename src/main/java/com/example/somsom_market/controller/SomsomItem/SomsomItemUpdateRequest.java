package com.example.somsom_market.controller.SomsomItem;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SomsomItemUpdateRequest {
    private String title;
    private int price;
    private String description;
//    private String imageUrl;
}
