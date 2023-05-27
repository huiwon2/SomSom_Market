package com.example.somsom_market.domain;

import lombok.*;
import org.hibernate.annotations.Table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Entity
@Table(appliesTo = "GROUPITEM")
public class GroupItem extends Item{

    private int sellerId;
    private int salesTarget;
    private int salesNow;
    private Date startDate;
    private Date endDate;
    private int status;
}
