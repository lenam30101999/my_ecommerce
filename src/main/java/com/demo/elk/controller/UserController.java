package com.demo.elk.controller;

import com.demo.elk.dto.userDTO.CreditCardDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController extends BaseController{

    @CrossOrigin(origins = "*")
    @GetMapping
    public ResponseEntity<?> getUserById(@RequestHeader("Authorization") String header){
        return userService.findUserById(header);
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path = "/{userId}/credit-cards")
    public ResponseEntity<?> addCreditCard(@RequestBody CreditCardDTO creditCardDTO,
                                           @RequestHeader("Authorization") String header){
        return userService.addCreditCard(creditCardDTO, header);
    }

    @CrossOrigin(origins = "*")
    @PutMapping(path = "/{userId}/credit-cards/{cardId}")
    public ResponseEntity<?> editCreditCard(@RequestBody CreditCardDTO creditCardDTO,
                                           @PathVariable("cardId") int cardId,
                                           @RequestHeader("Authorization") String header){
        return userService.updateCreditCard(creditCardDTO, cardId, header);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping(path = "/{userId}/credit-cards/{cardId}")
    public ResponseEntity<?> deleteCreditCard(@PathVariable("userId") int userId,
                                              @PathVariable("cardId") int cardId,
                                            @RequestHeader("Authorization") String header){
        return userService.deleteCreditCard(cardId, header);
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path = "/{userId}/credit-cards")
    public ResponseEntity<?> getAllCreditCard(@PathVariable("userId") int userId,
                                              @RequestHeader("Authorization") String header){
        return userService.getAllCreditCard(header);
    }
}
