package com.demo.elk.entity.shop;

import com.demo.elk.entity.BaseEntity;
import com.sun.mail.imap.protocol.ID;
import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "item")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "item_basic_id", nullable = false, referencedColumnName = "id")
    private ItemBasic itemBasic;

    @JoinColumn(name = "shop_id", nullable = false, referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Shop shop;
}
