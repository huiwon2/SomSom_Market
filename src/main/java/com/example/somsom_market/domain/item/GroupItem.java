package com.example.somsom_market.domain.item;

import com.example.somsom_market.domain.item.Item;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter @Setter
@Entity
@DiscriminatorValue(value="GROUP")
public class GroupItem extends Item implements Serializable {

    @Column(name="SALES_TARGET")
    private int salesTarget; //공구 목표액
    @Column(name="SALES_NOW")
    private int salesNow; //현재 모금액
    @Column(name="START_DATE")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate; //공구 시작일
    @Column(name="END_DATE")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate; //공구 마감일
}
