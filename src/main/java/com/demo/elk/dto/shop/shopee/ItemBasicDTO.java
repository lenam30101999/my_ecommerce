package com.demo.elk.dto.shop.shopee;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemBasicDTO {

    @JsonProperty("itemid")
    private long itemId;
    @JsonProperty("shopid")
    private long shopId;
    private String name;
    @JsonProperty("label_ids")
    private Object labelIds;
    private String image;
    private List<String> images;
    private String currency;
    private long stock;
    private long status;
    private long ctime;
    private long sold;
    @JsonProperty("historical_sold")
    private long historicalSold;
    @JsonProperty("liked_count")
    private long likedCount;
    @JsonProperty("view_count")
    private long viewCount;
    @JsonProperty("catid")
    private long catId;
    private String brand;
    @JsonProperty("cmt_count")
    private long cmtCount;
    private long flag;
    @JsonProperty("cb_option")
    private long cbOption;
    @JsonProperty("item_status")
    private String itemStatus;
    private long price;
    @JsonProperty("price_min")
    private long priceMin;
    @JsonProperty("price_max")
    private long priceMax;
    @JsonProperty("price_min_before_discount")
    private long priceMinBeforeDiscount;
    @JsonProperty("price_max_before_discount")
    private long priceMaxBeforeDiscount;
    @JsonProperty("price_before_discount")
    private long priceBeforeDiscount;
    @JsonProperty("show_discount")
    private long showDiscount;
    @JsonProperty("raw_discount")
    private long rawDiscount;
    private String discount;
    @JsonProperty("tier_variations")
    private List<TierVariationDTO> tierVariations;
    @JsonProperty("add_on_deal_info")
    private AddOnDealInfoDTO addOnDealInfo;
    @JsonProperty("shop_location")
    private String shopLocation;
    @JsonProperty("voucher_info")
    private VoucherInfoDTO voucherInfo;

    private boolean has_lowest_price_guarantee;
    private Object size_chart;
    private Object video_info_list;
    @JsonProperty("item_rating")
    private Object itemRating;
    private Object item_type;
    private Object reference_item_id;
    private Object transparent_background_image;
    private Object is_adult;
    private Object badge_icon_type;
    private Object shopee_verified;
    private Object is_official_shop;
    private Object show_official_shop_label;
    private Object show_shopee_verified_label;
    private Object show_official_shop_label_in_title;
    private Object is_cc_installment_payment_eligible;
    private Object is_non_cc_installment_payment_eligible;
    private Object coin_earn_label;
    private Object show_free_shipping;
    private Object preview_info;
    private Object coin_info;
    private Object exclusive_price_info;
    private Object bundle_deal_id;
    private Object can_use_bundle_deal;
    private Object bundle_deal_info;
    private Object is_group_buy_item;
    private Object has_group_buy_stock;
    private Object group_buy_info;
    private Object welcome_package_type;
    private Object welcome_package_info;
    private Object can_use_wholesale;
    private Object is_preferred_plus_seller;
    private Object has_model_with_available_shopee_stock;
    private Object can_use_cod;
    private Object is_on_flash_sale;
    private Object spl_installment_tenure;
    private Object is_live_streaming_price;
    private Object is_mart;
    private Object pack_size;
    private boolean liked;
    private Object hidden_price_display;
    private Object is_category_failed;
}
