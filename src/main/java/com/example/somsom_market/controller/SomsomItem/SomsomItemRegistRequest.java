package com.example.somsom_market.controller.SomsomItem;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;


@Data
public class SomsomItemRegistRequest {
    private String title;
    private int price;
    private String description;
    private MultipartFile imgFile;
//    private String imageUrl;
}
