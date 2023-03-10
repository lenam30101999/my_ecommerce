package com.demo.elk.service.impl;

import com.demo.elk.dto.ResponseDTO;
import com.demo.elk.dto.authentication.*;
import com.demo.elk.entity.types.OptionType;
import com.demo.elk.entity.types.OtpType;
import com.demo.elk.entity.types.State;
import com.demo.elk.entity.user.CustomUserDetails;
import com.demo.elk.entity.user.User;
import com.demo.elk.exception.ErrorException;
import com.demo.elk.exception.MessageResponse;
import com.demo.elk.service.AuthService;
import com.demo.elk.service.BaseService;
import com.demo.elk.util.CacheKey;
import com.demo.elk.util.Helper;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

@Log4j2
@Service
@Transactional
public class AuthServiceImpl extends BaseService implements AuthService {

    @Override
    public ResponseEntity<?> register(SignUpRequestDTO signUpRequestDTO, HttpServletRequest request) {
        checkUser(signUpRequestDTO);
        saveUserAndSendRegister(signUpRequestDTO, false, request);
        ResponseDTO response = new ResponseDTO(MessageResponse.REGISTER_SUCCESS, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> createAccount(SignUpRequestDTO signUpRequestDTO, HttpServletRequest request) {
        checkUser(signUpRequestDTO);
        saveUserAndSendRegister(signUpRequestDTO, true, request);
        ResponseDTO response = new ResponseDTO(MessageResponse.REGISTER_SUCCESS, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> login(LoginRequestDTO loginRequestDTO) {
        ResponseDTO response;
        User user = checkFormatUsername(loginRequestDTO.getUsername());
        checkCaseUser(user);
        try {
            LoginResponseDTO loginResponse = getLoginResponse(loginRequestDTO, user);
            String cacheKey = CacheKey.genRefreshKey(user.getId());
            cacheHandle.set(cacheKey, loginResponse.getRefreshToken(), expiryTimeRefreshToken);

            user.setLoginFailure(0);
            user.setRemoteAddress(loginRequestDTO.getRemoteAddress());
            userRepository.saveAndFlush(user);

            response = new ResponseDTO(loginResponse, BLANK_CHARACTER, HttpStatus.OK.value(), BLANK_CHARACTER);
        } catch (Exception e) {
            user.setLoginFailure(user.getLoginFailure() + 1);
            userRepository.save(user);

            throw new ErrorException(MessageResponse.PASSWORD_DIFFERENT);
        }
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> sendOtp(SendOtpDTO sendOtpDTO) throws IOException {
        String type = sendOtpDTO.getOtpType();
        OtpType otpType = type.equalsIgnoreCase(OtpType.REGISTER.name())
                ? OtpType.REGISTER
                : type.equalsIgnoreCase(OtpType.FORGOT_PASSWORD.name())
                ? OtpType.FORGOT_PASSWORD : null;
        if (Objects.isNull(otpType)) throw new ErrorException(MessageResponse.SOMETHING_WRONG);
        sendOtpToUser(sendOtpDTO.getUsername(), otpType);
        ResponseDTO response = new ResponseDTO(MessageResponse.SEND_SUCCESS, HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> verifyOtpRegister(VerifyDTO verifyDTO) {
        String username = verifyDTO.getUsername();
        String cacheKey = genCacheKeyOtp(username, OtpType.REGISTER);
        Integer otpCache = cacheHandle.get(cacheKey, Integer.class);
        if (Objects.isNull(otpCache)) {
            throw new ErrorException(MessageResponse.OTP_NOT_EXIST);
        }
        return handleActiveUser(username);
    }

    @Override
    public ResponseEntity<?> changeNewPassword(ChangePasswordDTO changePasswordDTO, Integer userId) {
        User user = findUserById(userId);
        if (Objects.isNull(user)) {
            throw new ErrorException(MessageResponse.USER_HAS_NOT_EXIST);
        }
        comparePassword(changePasswordDTO.getNewPassword(), changePasswordDTO.getConfirmPassword());
        comparePasswordEncrypt(changePasswordDTO.getNewPassword(), user.getPassword());
        return handleChangePassword(user, changePasswordDTO.getNewPassword());
    }

    @Override
    public ResponseEntity<?> logout(Integer userId) {
        ResponseDTO response;
        String cacheKey = CacheKey.genRefreshKey(userId);
        cacheHandle.del(cacheKey);
        response = new ResponseDTO(MessageResponse.USER_LOGOUT, HttpStatus.OK);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> changeNewPasswordWhenForgot(ForgotPwdDTO forgotPwdDTO) {
        String username = forgotPwdDTO.getUsername();
        String cacheKey = genCacheKeyOtp(username, OtpType.FORGOT_PASSWORD);
        Integer otpCache = cacheHandle.get(cacheKey, Integer.class);
        if (Objects.isNull(otpCache)) {
            throw new ErrorException(MessageResponse.OTP_NOT_EXIST);
        }
        User user = checkFormatUsername(username);
        comparePassword(forgotPwdDTO.getNewPassword(), forgotPwdDTO.getNewConfirmPassword());
        return handleChangePassword(user, forgotPwdDTO.getNewPassword());
    }

    private ResponseEntity<?> handleChangePassword(User user, String newPassword) {
        ResponseDTO response;
        if (Objects.nonNull(user)) {
            user.setPassword(encodePassword(newPassword));
            userRepository.saveAndFlush(user);
            response = new ResponseDTO(MessageResponse.PASSWORD_CHANGED_SUCCESS, HttpStatus.OK);
            return ResponseEntity.ok(response);
        }
        response = new ResponseDTO(MessageResponse.PASSWORD_CHANGED_FAILED, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<?> handleActiveUser(String username) {
        ResponseDTO response;
        User user = checkFormatUsername(username);

        if (Objects.isNull(user)) {
            throw new ErrorException(MessageResponse.USER_HAS_NOT_EXIST);
        }
        user.setState(State.ACTIVE);
        userRepository.saveAndFlush(user);

        String cacheKey = genCacheKey(OtpType.REGISTER, username);
        cacheHandle.del(cacheKey);
        response = new ResponseDTO(MessageResponse.ACCOUNT_ACTIVE_SUCCESS, HttpStatus.OK);
        return ResponseEntity.ok(response);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findUserByUsernameOrPhoneNumberOrEmail(username, username, username);
        return CustomUserDetails.create(user);
    }

    @Override
    public UserDetails loadUserById(int id) {
        User user = findUserById(id);
        if (Objects.isNull(user)) {
            throw new ErrorException(MessageResponse.USER_HAS_NOT_EXIST);
        }
        return CustomUserDetails.create(user);
    }

    public void saveUserAndSendRegister(SignUpRequestDTO signUpRequestDTO, boolean isAdmin, HttpServletRequest request) {
        try {
            User saved = assignUserDTOToUser(signUpRequestDTO, isAdmin, request);
            saved = userRepository.save(saved);
            if (!isAdmin) {
                String username = getPhoneNoOrEmailFromSignUpRequest(saved, signUpRequestDTO.getOption());
                sendOtpToUser(username, OtpType.REGISTER);
            }
        } catch (Exception e) {
            log.error("Error: {}", e.getMessage(), e);
            throw new ErrorException(e.getMessage());
        }
    }

    public void sendOtpToUser(String username, OtpType otpType) throws IOException {
        int otp = Helper.generateOTP();
        String cacheKey = genCacheKey(otpType, username);
        cacheHandle.set(cacheKey, otp, expiryTimeRegister);
        callSendOtp(username, otp, otpType);
    }

    private String getPhoneNoOrEmailFromSignUpRequest(User user, String option) {
        return option.equalsIgnoreCase(OptionType.EMAIL.toString())
                ? Objects.nonNull(user.getEmail()) ? user.getEmail() : user.getPhoneNumber()
                : user.getPhoneNumber();
    }

    private void checkCaseUser(User user) {
        if (user == null)
            throw new ErrorException(MessageResponse.USER_HAS_NOT_EXIST);
        if (user.getState() == State.NONACTIVE)
            throw new ErrorException(MessageResponse.ACCOUNT_HAS_NOT_ACTIVE);
        if (user.getState() == State.BLOCKED)
            throw new ErrorException(MessageResponse.USER_HAS_BANNED);
    }

    private void callSendOtp(String username, int otp, OtpType otpType) {
        String content = null;
        if (otpType.equals(OtpType.REGISTER)) {
            content = String.format(MessageResponse.OTP_MESSAGE_REGISTER, otp);
        }
        if (otpType.equals(OtpType.FORGOT_PASSWORD)) {
            content = String.format(MessageResponse.OTP_MESSAGE_FORGOT_PASSWORD, otp);
        }
        sentToContact(username, content);
    }

    private void sentToContact(String username, String content) {
        if (Helper.regexEmail(username)) {
            otpService.sentMessageByEmail(username, content);
        }
        if (Helper.regexPhoneNumber(username)) {
            otpService.sentMessageByPhoneNo(username, content);
        }
    }


    private void comparePasswordEncrypt(String newPassword, String currentPassword) {
        boolean checkOldPassPwd = BCrypt.checkpw(newPassword, currentPassword);
        if (!checkOldPassPwd) {
            throw new ErrorException(MessageResponse.OLD_PASSWORD_DIFFERENT);
        }
    }

    private void comparePassword(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new ErrorException(MessageResponse.PASSWORD_DIFFERENT);
        }
    }

    private String genCacheKey(OtpType otpType, String username) {
        String cacheKey = null;
        if (Helper.regexPhoneNumber(username)) {
            cacheKey = CacheKey.genRegPhoneKey(otpType, username);
        } else if (Helper.regexEmail(username)) {
            cacheKey = CacheKey.genRegEmailKey(otpType, username);
        }
        return cacheKey;
    }

    private String genCacheKeyOtp(String username, OtpType otpType) {
        String cacheKey = null;
        if (Helper.regexPhoneNumber(username)) {
            cacheKey = CacheKey.genRegPhoneKey(otpType, username);
        } else if (Helper.regexEmail(username)) {
            cacheKey = CacheKey.genRegEmailKey(otpType, username);
        }
        return cacheKey;
    }

    private void checkUser(SignUpRequestDTO signUpRequestDTO) {
        User user = checkFormatUsername(signUpRequestDTO.getUsername());
        if (Objects.nonNull(user)) {
            throw new ErrorException(MessageResponse.USER_HAS_EXIST);
        }
        comparePassword(signUpRequestDTO.getPassword(), signUpRequestDTO.getConfirmPassword());
    }

    private User checkFormatUsername(String username) {
        User user;
        if (Helper.regexPhoneNumber(username)) {
            user = userRepository.findUserByPhoneNumber(username);
        } else if (Helper.regexUsername(username)) {
            user = userRepository.findUserByUsername(username);
        } else if (Helper.regexEmail(username)) {
            String email = username.toLowerCase();
            user = userRepository.findUserByEmail(email);
        } else {
            throw new ErrorException(MessageResponse.WRONG_FORMAT);
        }
        return user;
    }
}
