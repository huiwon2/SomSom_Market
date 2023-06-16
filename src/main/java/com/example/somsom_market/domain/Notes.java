package com.example.somsom_market.domain;

import com.example.somsom_market.domain.item.PersonalItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "NOTES")
@SequenceGenerator(name="SEQ_NOTES", sequenceName="NOTES_ID_SEQ", allocationSize=1)
public class Notes {
    @Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_NOTES")
    @Column(name = "notes_id")
    private Long notesId;

    @Column(name = "from_account_id")
    private String fromAccountId;
    @Column(name = "from_item_id")
    private Long fromItemId;
    @Column(name = "to_seller_id")
    private String toSellerId;

    @Transient
    private String toSellerNickName;
    @Transient
    private String fromAccountNickName;

    private String title;
    private String content;
    @Column(name = "sended_at")
    private Date sendedAt;
    @Column(name = "readed_at")
    private Date readedAt;
    @Column(name = "from_del")
    private String fromDel;
    @Column(name = "to_del")
    private String toDel;

    @Transient
    private String sendDate;
    @Transient
    private String readDate;
    @Transient
    private String person; // 수신자인지 발신자인지 저장
}
