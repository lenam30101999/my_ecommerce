package com.demo.elk.service.impl;

import com.demo.elk.dto.shop.shopee.ShopDTO;
import com.demo.elk.service.BaseService;
import com.demo.elk.service.ShopService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@Transactional
public class ShopServiceImpl extends BaseService implements ShopService {

    public void saveDataCron(ShopDTO shopDTO){
        try {
            saveShop(shopDTO);
        }catch (Exception e) {
            log.info(e);
        }
    }
}
