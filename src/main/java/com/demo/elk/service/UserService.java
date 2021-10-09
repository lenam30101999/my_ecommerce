package com.demo.elk.service;

import com.demo.elk.dto.userDTO.AccountStateDTO;
import com.demo.elk.dto.userDTO.CreditCardDTO;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<?> findUserById(String header);

    ResponseEntity<?> addCreditCard(CreditCardDTO creditCardDTO, String header);

    ResponseEntity<?> updateCreditCard(CreditCardDTO creditCardDTO, int cardId, String header);

    ResponseEntity<?> deleteCreditCard(int cardId, String header);

    ResponseEntity<?> getAllCreditCard(String header);

    ResponseEntity<?> editStateAccount(AccountStateDTO accountStateDTO);

}
