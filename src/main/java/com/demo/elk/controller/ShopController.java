package com.demo.elk.controller;

import com.demo.elk.dto.shop.shopee.ShopDTO;
import com.demo.elk.service.impl.ShopServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/shops")
public class ShopController {

    @Autowired
    private ShopServiceImpl shopService;

    @PostMapping
    public ResponseEntity<?> saveItemShop(@Valid @RequestBody ShopDTO shopDTO){
        shopService.saveDataCron(shopDTO);
        return ResponseEntity.ok(shopDTO);
    }
}
