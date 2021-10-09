package com.demo.elk.controller;

import com.demo.elk.dto.authentication.SignUpRequestDTO;
import com.demo.elk.dto.userDTO.AccountStateDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Log4j2
@RestController
@RequestMapping("/api/v1/admins")
public class AdminController extends BaseController {

    @PostMapping(path = "/create-account")
    public ResponseEntity<?> createAccount(@Valid @RequestBody SignUpRequestDTO signUpRequestDTO) {
        return authService.createAccount(signUpRequestDTO);
    }

    @PutMapping(path = "/account-state")
    public ResponseEntity<?> activeForUser(@RequestBody AccountStateDTO accountStateDTO) {
        return userService.editStateAccount(accountStateDTO);
    }

}
