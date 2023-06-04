package com.example.somsom_market.domain;

import lombok.*;
import org.hibernate.annotations.Table;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("SOMSOM")
@Data
public class SomsomItem extends Item {

    private int stockQuantity;
    @OneToMany(mappedBy = "item")
    private List<CartItem> cartItemList = new ArrayList<>();
    //==비즈니스 로직==//
    /**
     * stock 증가
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /**
     * stock 감소
     */
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            //throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }
}
