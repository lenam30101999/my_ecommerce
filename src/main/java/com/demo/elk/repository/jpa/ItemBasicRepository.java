package com.demo.elk.repository.jpa;

import com.demo.elk.entity.shop.ItemBasic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemBasicRepository extends JpaRepository<ItemBasic, Integer> {
    Optional<ItemBasic> findItemBasicByItemId(long id);
}
