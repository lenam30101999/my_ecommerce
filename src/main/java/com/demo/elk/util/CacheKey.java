package com.demo.elk.util;

import com.demo.elk.entity.types.OtpType;
import lombok.Data;

@Data
public class CacheKey {
    public static final String KEY_REFRESH = "refresh_";
    public static final String KEY_ACCESS = "access_";

    public static String genRefreshKey(int userId){
        return KEY_REFRESH + userId;
    }

    public static String genAccessKey(int userId){
        return KEY_ACCESS + userId;
    }

    public static String genRegPhoneKey(OtpType otpType, String phoneNumber) {
        return otpType.name() + "_phone_no_" + phoneNumber;
    }

    public static String genRegEmailKey(OtpType otpType, String email) {
        return otpType.name() + "_email_" + email;
    }
}
