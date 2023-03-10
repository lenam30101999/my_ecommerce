package com.demo.elk.service.impl;

import com.demo.elk.dto.ResponseDTO;
import com.demo.elk.dto.userDTO.AccountStateDTO;
import com.demo.elk.dto.userDTO.CreditCardDTO;
import com.demo.elk.dto.userDTO.UserDTO;
import com.demo.elk.entity.types.BankName;
import com.demo.elk.entity.types.CreditType;
import com.demo.elk.entity.types.State;
import com.demo.elk.entity.user.CreditCard;
import com.demo.elk.entity.user.User;
import com.demo.elk.entity.user.UserElasticsearch;
import com.demo.elk.exception.ErrorException;
import com.demo.elk.exception.MessageResponse;
import com.demo.elk.service.BaseService;
import com.demo.elk.service.UserService;
import com.demo.elk.util.MessageUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@Log4j2
public class UserServiceImpl extends BaseService implements UserService {

    @Override
    public ResponseEntity<?> findUserById(String header) {
        int id = getJwt(header);
        User user = findUserById(id);
        UserDTO userDTO = modelMapper.convertUserToDTO(user);
        return ResponseEntity.ok(new ResponseDTO(userDTO, BLANK_CHARACTER, HttpStatus.OK, BLANK_CHARACTER));
    }

    @Override
    public ResponseEntity<?> addCreditCard(CreditCardDTO creditCardDTO, String header) {
        int id = getJwt(header);
        User user = findUserById(id);
        checkCreditCardExisted(creditCardDTO.getCreditCardNumber(), id);

        CreditCard saved = CreditCard.builder()
                .creditCardNumber(creditCardDTO.getCreditCardNumber())
                .bankName(getBankName(creditCardDTO.getBankName()))
                .creditType(getCreditType(creditCardDTO.getCreditType()))
                .totalMoney(0L)
                .user(user)
                .build();
        iCreditCardRepository.save(saved);
        return ResponseEntity.ok(new ResponseDTO(MessageResponse.ADD_SUCCESS, HttpStatus.OK));
    }

    @Override
    public ResponseEntity<?> updateCreditCard(CreditCardDTO creditCardDTO, int cardId, String header) {
        int id = getJwt(header);
        User user = findUserById(id);
        checkCreditCardExisted(creditCardDTO.getCreditCardNumber(), id);

        CreditCard updated = iCreditCardRepository.findById(cardId)
                .orElseThrow(() -> new ErrorException(MessageResponse.CREDIT_CARD_NOT_FOUND));
        if (user.getId() != updated.getUser().getId()) {
            throw new ErrorException(MessageResponse.UPDATE_NOT_SUCCESS);
        }

        updated.setCreditCardNumber(creditCardDTO.getCreditCardNumber());
        updated.setBankName(getBankName(creditCardDTO.getBankName()));
        updated.setCreditType(getCreditType(creditCardDTO.getCreditType()));
        iCreditCardRepository.saveAndFlush(updated);

        return ResponseEntity.ok(new ResponseDTO(MessageResponse.UPDATE_SUCCESS, HttpStatus.OK));
    }

    @Override
    public ResponseEntity<?> deleteCreditCard(int cardId, String header) {
        int id = getJwt(header);
        User user = findUserById(id);
        CreditCard deleted = iCreditCardRepository.findById(cardId)
                .orElseThrow(() -> new ErrorException(MessageResponse.CREDIT_CARD_NOT_FOUND));
        if (Objects.isNull(deleted) || user.getId() != deleted.getUser().getId()) {
            throw new ErrorException(MessageResponse.DELETE_NOT_SUCCESS);
        }

        iCreditCardRepository.delete(deleted);
        return ResponseEntity.ok(new ResponseDTO(MessageResponse.UPDATE_SUCCESS, HttpStatus.OK));
    }

    @Override
    public ResponseEntity<?> getAllCreditCard(String header) {
        int id = getJwt(header);
        User user = findUserById(id);
        List<CreditCard> creditCards = iCreditCardRepository.findAllByUserId(user.getId())
                .orElseThrow(() -> new ErrorException(MessageResponse.CREDIT_CARD_NOT_FOUND));
        List<CreditCardDTO> creditCardDTOs =
                creditCards.stream().map(modelMapper::convertCreditCardToCreditCardDTO).collect(Collectors.toList());
        return ResponseEntity.ok(new ResponseDTO(creditCardDTOs, BLANK_CHARACTER, HttpStatus.OK.value(), BLANK_CHARACTER));
    }

    @Override
    public ResponseEntity<?> editStateAccount(AccountStateDTO accountStateDTO) {
        User user = findUserById(accountStateDTO.getUserId());
        State state = accountStateDTO.getState();

        if (state == State.ACTIVE) {
            user.setState(State.ACTIVE);
        } else if (state == State.BLOCKED) {
            user.setState(State.BLOCKED);
        }

        userRepository.saveAndFlush(user);
        return ResponseEntity.ok(new ResponseDTO(MessageResponse.UPDATE_SUCCESS, HttpStatus.OK));
    }

    @Override
    public ResponseEntity<?> findAllUser(List<Integer> userIds) {
        List<User> users = userRepository.findUser(userIds);
        List<UserDTO> userDTOS = users.stream().map(modelMapper::convertUserToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(userDTOS);
    }

    @Override
    public ResponseEntity<?> findAllUserByFullNameLike(String name) {
        List<UserElasticsearch> users = userElasticsearchRepository.findFuzzyByFullName(name);
        return ResponseEntity.ok(users);
    }

    @Override
    public ResponseEntity<?> deleteAccount(int userId) {
        userRepository.deleteById(userId);
        return ResponseEntity.ok(MessageUtils.MSG_DELETE_SUCCESS);
    }

    private void checkCreditCardExisted(String creditCardNumber, int userId) {
        CreditCard creditCard =
                iCreditCardRepository.findByCreditCardNumberAndUserId(creditCardNumber, userId).orElse(null);
        if (Objects.nonNull(creditCard)) {
            throw new ErrorException(MessageResponse.CREDIT_CARD_HAS_EXIST);
        }
    }

    private BankName getBankName(String bankName) {
        for (BankName name : BankName.values()) {
            if (name.name().equalsIgnoreCase(bankName)) {
                return name;
            }
        }
        throw new ErrorException(MessageResponse.WRONG_BANK_NAME);
    }

    private CreditType getCreditType(String creditType) {
        return Arrays.stream(CreditType.values())
                .filter(type -> type.name().equalsIgnoreCase(creditType))
                .findFirst()
                .orElseThrow(() -> new ErrorException(MessageResponse.WRONG_CREDIT_CARD_TYPE));
    }
}
