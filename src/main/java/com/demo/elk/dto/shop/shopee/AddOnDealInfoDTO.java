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
public class AddOnDealInfoDTO {

    @JsonProperty("add_on_deal_id")
    private int addOnDealId;

    @JsonProperty("add_on_deal_label")
    private String addOnDealLabel;

    @JsonProperty("sub_type")
    private int subType;

    private int status;
}
