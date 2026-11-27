package com.scw.scrowlifebackend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailCodeService {
    private final JavaMailSender emailSender;
    private final StringRedisTemplate redisTemplate;

    @Value("${spring.mail.username}")
    private String emailFrom;

    @Value("${verify-code.expire-minutes}")
    private Integer expires;

    public void sendCode(String toEmail){
        //控制同一用户的验证码两次发送间隔
        String redisMailCodeRateKey = "email:rate:"+toEmail;
        if(redisTemplate.hasKey(redisMailCodeRateKey)){
            throw new IllegalStateException("发送验证码过于频繁，请稍后再试");
        }
        //生成code
        String redisMailCodeKey = "email:code:"+ toEmail;
        String code = generateCode();

        //组织模板并发送邮件
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailFrom);
        message.setTo(toEmail);
        message.setSubject("【验证码】您的CodeYell验证码");
        message.setText(String.format(
                "您好，您的验证码为：%s\n\n该验证码%d分钟内有效，请勿泄露给他人。\n如非本人操作，请忽略此邮件。",
                code, expires
        ));
        emailSender.send(message);
        // 设置指定过期时间
        redisTemplate.opsForValue().set(redisMailCodeKey,code,expires,TimeUnit.MINUTES);
        log.info("获取验证码1: {}", redisMailCodeKey,redisTemplate.opsForValue().get(redisMailCodeKey));
        //设置60s发送间隔
        redisTemplate.opsForValue().set(redisMailCodeRateKey, "1", 60, TimeUnit.SECONDS);

        log.info("验证码邮件发送成功: {}", toEmail);

    };

    public Boolean checkCode(String code,String toEmail){
        String redisMailCodeKey = "email:code:"+ toEmail;
        String existedCode = redisTemplate.opsForValue().get(redisMailCodeKey);
        log.info("获取验证码2: {},{}", redisMailCodeKey,existedCode);
        if(existedCode!=null && existedCode.equals(code)){
            return  true;
        }
        return  false;
    }

    //随机生成6位验证码
    private String generateCode(){
        SecureRandom random  = new SecureRandom();
        StringBuilder code = new StringBuilder();
        for(int i=0;i<6;i++){
            code.append(random.nextInt(10));
        }
        return code.toString();
    }
}

