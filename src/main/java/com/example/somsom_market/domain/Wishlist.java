package com.example.somsom_market.domain;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Entity
@Table(name="WISHLIST")
public class Wishlist {
    @Id @GeneratedValue
    @Column(name = "wishlist_id")
    private long wishlistId;
    @Column(name = "user_id")
    private String userId;

    // private Item item; // 잠깐 고민을...
}
