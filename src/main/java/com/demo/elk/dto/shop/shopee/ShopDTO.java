package com.demo.elk.dto.shop.shopee;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopDTO {

    @JsonProperty("bff_meta")
    private String bffMeta;

    private String error;

    @JsonProperty("error_msg")
    private String errorMsg;

    @JsonProperty("reserved_keyword")
    private String reservedKeyword;

    @JsonProperty("suggestion_algorithm")
    private String suggestionAlgorithm;

    @JsonProperty("algorithm")
    private String algorithm;

    @JsonProperty("total_count")
    private int totalCount;

    private boolean nomore;

    @JsonProperty("items")
    private List<ItemDTO> items;

    @JsonProperty("price_adjust")
    private Object priceAdjust;

    private Object adjust;

    @JsonProperty("total_ads_count")
    private int totalAdsCount;

    @JsonProperty("hint_keywords")
    private Object hintKeywords;

    @JsonProperty("show_disclaimer")
    private boolean showDisclaimer;

    @JsonProperty("json_data")
    private Object jsonData;

    @JsonProperty("query_rewrite")
    private Object queryRewrite;

    @JsonProperty("disclaimer_infos")
    private Object disclaimerInfors;

    @JsonProperty("need_next_search")
    private boolean needNextSearch;

    @JsonProperty("low_result")
    private Object lowResult;

    @JsonProperty("autoplay_info")
    private Object autoplayInfo;

    @JsonProperty("food_item_info")
    private Object foodItemInfo;

    @JsonProperty("search_tracking")
    private String searchTracking;
}
