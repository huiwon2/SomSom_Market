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
@Table(appliesTo = "ACCOUNT")
public class Account {
    @Id
    @GeneratedValue
    private int userId;
    @NotNull
    private String userName;
    private String nickName;
    @NotNull
    private String id;
    @NotNull
    private String password;
    private String email;
    private String address;
    private String bankName;
    private String bankAccount;
    private String phone;
//    수정 필요
    private List<Integer> wishItem;

}
