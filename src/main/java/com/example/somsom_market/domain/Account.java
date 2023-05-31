package com.example.somsom_market.domain;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Entity
@Table(name="ACCOUNT")
public class Account {
    @Id
    private String id;
    @NotNull @Column(name = "user_name")
    private String userName;
    @NotNull @Column(name = "nick_name")
    private String nickName;
    @NotNull
    private String password;
    @NotNull
    private String email;
    private String address;
    private String zipcode;
    @Column(name = "bank_name")
    private String bankName;
    @Column(name = "bank_account")
    private String bankAccount;
    @NotNull
    private String phone;
//    수정 필요
    // private List<Integer> wishItem;

}
