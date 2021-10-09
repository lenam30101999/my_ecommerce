package com.demo.elk.service.impl;

import com.demo.elk.service.BaseService;
import com.demo.elk.util.Helper;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Log4j2
public class OtpService extends BaseService {

    @Value("${twilio.account_sid}")
    private String accountSid;

    @Value("${twilio.auth_token}")
    private String authToken;

    @Value("${twilio.twilio_number}")
    private String twilioNumber;

    private final String subject = "Trung tâm quản lý tài khoản";

    public void sentMessageByPhoneNo(String toPhoneNumber, String content){
        Twilio.init(accountSid, authToken);
        Message message = Message.creator(
                new PhoneNumber(Helper.formatNumber(toPhoneNumber)),
                new PhoneNumber(twilioNumber),
                content
        ).create();
        log.info("Send success: " + message.getSid());
    }

    public void sentMessageByEmail(String email, String content){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@gmail.com");
        message.setTo(email);
        message.setSubject(subject);
        message.setText(content);
        javaMailSender.send(message);
    }
}
