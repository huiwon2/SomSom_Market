package com.example.somsom_market.domain.item;

import com.example.somsom_market.domain.ItemStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@SequenceGenerator(name="SEQ_ITEM", sequenceName="ITEM_ID_SEQ", allocationSize=1)
@Getter
@Setter
public abstract class Item {

    @Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ITEM")
    @Column(name = "item_id")
    private Long id;

    @Column(name = "seller_id")
    private String sellerId;
    private String title;
    private String description;
    private int price;
    @Column(name = "wish_count")
    private int wishCount;

    @Enumerated(EnumType.STRING)
    private ItemStatus status;

    @Column(name = "img_name")
    private String imgName;
    @Column(name = "img_path")
    private String imgPath;

    @Column(insertable = false, updatable = false)
    private String dtype;
}
