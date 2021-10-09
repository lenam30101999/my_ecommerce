package com.demo.elk.entity.shop;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "item_basic")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemBasic implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "item_id", nullable = false, unique = true)
    private long itemId;

    private String name;

    private String currency;

    private long stock;

    private long status;

    private long ctime;

    private long sold;

    @Column(name = "historical_sold")
    private long historicalSold;

    @Column(name = "liked_count")
    private long likedCount;

    @Column(name = "view_count")
    private long viewCount;

    @Column(name = "catid")
    private long catId;

    private String brand;

    @Column(name = "cmt_count")
    private long cmtCount;

    private long flag;

    @Column(name = "cb_option")
    private long cbOption;

    @Column(name = "item_status")
    private String itemStatus;

    private long price;

    @Column(name = "price_min")
    private long priceMin;

    @Column(name = "price_max")
    private long priceMax;

    @Column(name = "price_min_before_discount")
    private long priceMinBeforeDiscount;

    @Column(name = "price_max_before_discount")
    private long priceMaxBeforeDiscount;

    @Column(name = "price_before_discount")
    private long priceBeforeDiscount;

    @Column(name = "show_discount")
    private long showDiscount;

    @Column(name = "raw_discount")
    private long rawDiscount;

    @Column(name = "shop_location")
    private String shopLocation;
}
