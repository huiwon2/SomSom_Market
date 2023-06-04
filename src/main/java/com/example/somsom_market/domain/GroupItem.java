package com.example.somsom_market.domain;

import lombok.*;
import org.hibernate.annotations.Table;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Entity
@Table(appliesTo = "ITEM")
@DiscriminatorValue(value="GROUP")
public class GroupItem extends Item implements Serializable {

    //@ManyToOne
    //@JoinColumn(name="seller", referencedColumnName="id")
    //private Account user; //1 : N(0..*) , many side
    @Column(name="seller_id")
    private String sellerId; // userId

    private int category_id;

    @Column(name="sales_target")
    private int salesTarget; //공구 목표액
    @Column(name="sales_now")
    private int salesNow; //현재 판매액
    @Column(name="start_date")
    private Date startDate; //공구 시작일
    @Column(name="end_date")
    private Date endDate; //공구 마감일
    private String status; //INSTOCK, SOLDOUT

    private String dtype; //GROUP - this is discriminator column
}
