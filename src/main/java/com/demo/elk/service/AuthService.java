package com.demo.elk.service;

import com.demo.elk.dto.authentication.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface AuthService extends UserDetailsService {
    ResponseEntity<?> register(SignUpRequestDTO signUpRequestDTO, HttpServletRequest request);

    ResponseEntity<?> createAccount(SignUpRequestDTO signUpRequestDTO, HttpServletRequest request);

    ResponseEntity<?> login(LoginRequestDTO loginRequestDTO);

    ResponseEntity<?> sendOtp(SendOtpDTO sendOtpDTO) throws IOException;

    ResponseEntity<?> verifyOtpRegister(VerifyDTO verifyDTO) throws Exception;

    ResponseEntity<?> changeNewPassword(ChangePasswordDTO changePasswordDTO, Integer userId);

    ResponseEntity<?> logout(Integer userId);

    ResponseEntity<?> changeNewPasswordWhenForgot(ForgotPwdDTO forgotPwdDTO) throws Exception;

    UserDetails loadUserById(int id);

    UserDetails loadUserByUsername(String username);

}
