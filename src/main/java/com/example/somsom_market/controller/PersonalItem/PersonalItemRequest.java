package com.example.somsom_market.controller.PersonalItem;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Data
public class PersonalItemRequest {
    private Long itemId;
    private String sellerId;
    private String imgName;
    private String imgPath;

    @NotBlank
    private String title;
    @PositiveOrZero
    private int price;
    @NotBlank
    private String description;
    // private List<String> imageUrl;
    private MultipartFile imgFile;
    @NotBlank
    private String status; // 거래가능 / 거래중 / 거래완료
}
