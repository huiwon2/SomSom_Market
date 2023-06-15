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
    private Long notes_id;

    private String from_account_id;
    private Long from_item_id;
    private String to_seller_id;

    @Transient
    private String to_seller_nickName;

    private String title;
    private String content;
    private Date sended_at;
    private Date readed_at;
    private String from_del;
    private String to_del;
}
