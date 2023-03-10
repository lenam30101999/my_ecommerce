package com.demo.elk.util;

import com.devskiller.friendly_id.FriendlyId;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helper {

    public static int generateOTP(){
        return new Random().nextInt(900000) + 100000;
    }

    public static String formatNumber(String phoneNumber){
        return phoneNumber.replace(String.valueOf(phoneNumber.charAt(0)), "+84");
    }

    public static boolean regexUsername(String username) {
        if (username == null) {
            return false;
        }
        String regex = "^[a-zA-Z0-9._]{6,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(username);
        return matcher.find();
    }

    public static boolean regexPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            return false;
        }
        String regex = "^0[37859][0-9]\\d{7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.find();
    }

    public static boolean regexEmail(String email) {
        if (email == null) {
            return false;
        }
        String regex =
                "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }

    public static String generateUid(){
        return FriendlyId.createFriendlyId();
    }
}
