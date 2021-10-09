package com.demo.elk.controller;

import com.demo.elk.dto.authentication.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@Log4j2
@RestController
@RequestMapping("/api/v1/authentications")
public class AuthenticationController extends BaseController {

    @PostMapping(path = "/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequestDTO signUpRequestDTO) {
        return authService.register(signUpRequestDTO);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        return authService.login(loginRequestDTO);
    }

    @PostMapping(path = "/send-otp")
    public ResponseEntity<?> resendCodeRegister(@RequestBody @Valid SendOtpDTO sendOtpDTO) throws IOException {
        return authService.sendOtp(sendOtpDTO);
    }

    @PutMapping(path = "/verify")
    public ResponseEntity<?> verifyOtpRegister(@RequestBody @Valid VerifyDTO verifyDTO) throws Exception {
        return authService.verifyOtpRegister(verifyDTO);
    }

    @PutMapping(path = "/change-password")
    public ResponseEntity<?> changeNewPassword(@RequestHeader(name = "x-user-id") Integer userId,
                                               @RequestBody @Valid ChangePasswordDTO dto) {
        return authService.changeNewPassword(dto, userId);
    }

    @DeleteMapping(path = "/logout")
    public ResponseEntity<?> logout(@RequestHeader(name = "x-user-id") Integer userId) {
        return authService.logout(userId);
    }

    @PutMapping(path = "/forgot-password")
    public ResponseEntity<?> changeNewPassword(@RequestBody @Valid ForgotPwdDTO forgotPwdDTO) throws Exception {
        return authService.changeNewPasswordWhenForgot(forgotPwdDTO);
    }

}
