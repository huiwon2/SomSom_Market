package com.example.somsom_market.domain.item;

import com.example.somsom_market.domain.item.Item;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter @Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Entity
@DiscriminatorValue(value="GROUP")
public class GroupItem extends Item implements Serializable {

    //@ManyToOne
    //@JoinColumn(name="seller", referencedColumnName="id")
    //private Account user; //1 : N(0..*) , many side
    @Column(name="seller_id")
    private String sellerId; // userId

    @Column(name="sales_target")
    private int salesTarget; //공구 목표액
    @Column(name="sales_now")
    private int salesNow; //현재 판매액
    @Column(name="start_date")
    private Date startDate; //공구 시작일
    @Column(name="end_date")
    private Date endDate; //공구 마감일
}
