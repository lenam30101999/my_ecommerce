package com.demo.elk.dto.shop.shopee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TierVariationDTO {
    private String name;
    private List<String> options;
    private Object images;
    private Object properties;
    private int type;
}
