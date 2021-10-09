package com.demo.elk.repository.jpa;

import com.demo.elk.entity.shop.AddOnDealInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddOnDealInfoRepository extends JpaRepository<AddOnDealInfo, Integer> {
}