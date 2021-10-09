package com.demo.elk.dto.shop.shopee;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDTO {

    @JsonProperty("item_basic")
    private ItemBasicDTO itemBasicDTO;

    @JsonProperty("itemid")
    private long itemId;

    @JsonProperty("shopid")
    private long shopId;

    @JsonProperty("adsid")
    private Object adsId;

    @JsonProperty("campaignid")
    private Object campaignId;

    private Object distance;
    private Object match_type;
    private Object ads_keyword;
    private Object deduction_info;
    private Object collection_id;
    private Object display_name;
    private Object campaign_stock;
    private Object json_data;
    private Object tracking_info;
    private Object algo_image;
    private Object fe_flags;
    private Object item_type;
    private Object foody_item;
    private Object search_item_tracking;
    private Object bff_item_tracking;
    private Object personalized_labels;
}
