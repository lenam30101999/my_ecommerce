package com.demo.elk.service;

import com.demo.elk.dto.shop.shopee.ShopDTO;

public interface ShopService {
    void saveDataCron(ShopDTO shopDTO);
}
