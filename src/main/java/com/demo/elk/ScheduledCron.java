package com.demo.elk;

import com.demo.elk.dto.shop.shopee.ShopDTO;
import com.demo.elk.service.impl.ShopServiceImpl;
import com.demo.elk.util.RequestUtil;
import com.demo.elk.util.Utils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;

@Log4j2
//@Component
public class ScheduledCron {

    @Autowired private ShopServiceImpl shopService;

    private static int limit = 0;

//    @Scheduled(cron = "*/10 * * * * *")
    public void schedulePullDataFromShop(){
        System.out.println(limit);
        String shopId = "25211549";
        String newest = "0";
        String requestUrl = String.format(Utils.URL_SHOP, limit, shopId, newest);
        log.info("Url: " + requestUrl);
        ShopDTO shopDTO = RequestUtil.request(HttpMethod.GET, requestUrl, ShopDTO.class, null, null);
        shopService.saveDataCron(shopDTO);
        limit += 30;
    }
}
