package com.example.somsom_market.domain;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.Table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Entity
@Table(appliesTo = "ITEM")
public class Item {
    @Id
    @GeneratedValue
    private int itemId;
    @NotNull
    private String title;
    @NotNull
    private int price;
    private String description;
//    list타입 수정 필요
    private List<String> imageUrl;
    private int wishCount;
}
