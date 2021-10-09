package com.demo.elk.repository.jpa;

import com.demo.elk.entity.user.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICreditCardRepository extends JpaRepository<CreditCard, Integer> {
    Optional<List<CreditCard>> findAllByUserId(int userId);
    Optional<CreditCard> findByCreditCardNumberAndUserId(String creditCardNumber, int userId);
}
