package com.example.somsom_market.domain.item;

import com.example.somsom_market.domain.item.Item;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.Date;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue(value="PERSONAL")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonalItem extends Item {

    @Transient
    private String nickName; // 화면에 닉네임 출력하기 위한 필드

    @Column(name="start_date")
    private Date startDate; // 글 올린 날짜 ㅎ

}
