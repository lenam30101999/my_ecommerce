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
public class VoucherInfoDTO {

    @JsonProperty("promotion_id")
    private int promotionId;

    @JsonProperty("voucher_code")
    private String voucherCode;

    private String label;
}
