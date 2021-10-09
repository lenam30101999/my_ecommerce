package com.demo.elk.repository.jpa;

import com.demo.elk.entity.shop.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
    List<Item> findAllByShopId(long shopId);
}
