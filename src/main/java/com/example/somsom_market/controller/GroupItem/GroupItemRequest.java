package com.example.somsom_market.controller.GroupItem;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Date;
import java.util.List;

@Data
public class GroupItemRequest {
    private Long itemId;
    @NotBlank
    private String title;
    @Positive
    private int price;
    @NotBlank
    private String description;
    private MultipartFile imgFile;
    private String imgPath;
    private int wishCount;
    private String sellerId;
    @Positive
    private int salesTarget; // 목표 금액
    @PositiveOrZero
    private int salesNow; // 현재 모금액

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    private String status;

}
