package com.example.somsom_market.domain.item;

import com.example.somsom_market.domain.item.Item;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue(value="PERSONAL")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonalItem extends Item {

    @Transient
    private String nickName; // 화면에 닉네임 출력하기 위한 필드

}
