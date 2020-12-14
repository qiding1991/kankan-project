package com.kankan.service;

import com.kankan.config.MailConfig;
import com.kankan.util.JiaGuoRandomUtil;

import lombok.Builder;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;


@Builder
@Data
class MailData {
    private String receiver;
    private String title;
    private String content;
}


@Log4j2
@Data
public class MailSender {


    private String emailTemplate;

    private Integer activeCodeLength;


    @Autowired
    private MailConfig mailConfig;

    public String sendActiveSmsCode(String receiver) {
        String activeCode = JiaGuoRandomUtil.generateCode(activeCodeLength);
        String content = String.format(emailTemplate, receiver, activeCode);
        MailData mailData = MailData.builder().content(content).receiver(receiver).build();
        try {
            this.sendEmail(mailConfig, mailData);
        } catch (EmailException e) {
            log.error("发送激活邮件失败,receiver={},config={},data={}", receiver, mailConfig, mailData, e);
           //TODO 异常处理 return null;
        }
        return activeCode;
    }

    public Boolean sendEmail(MailConfig jmailConfig, MailData mailData) throws EmailException {
        HtmlEmail email = new HtmlEmail();//创建电子邮件对象
        email.setDebug(true);
        email.setSSLCheckServerIdentity(true);
        email.setSmtpPort(465);
        email.setSSLOnConnect(true);
        email.setHostName(jmailConfig.getHost());//设置发送电子邮件使用的服务器主机名
        email.setAuthenticator(
                new DefaultAuthenticator(jmailConfig.getUserName(), jmailConfig.getPassword()));//邮件服务器身份验证
        email.setFrom(jmailConfig.getUserName());//设置发信人邮箱
        email.setSubject(mailData.getTitle());//设置邮件主题
        email.setMsg(mailData.getContent());//设置邮件文本内容
        email.addTo(mailData.getReceiver());//设置收件人
        email.send();//发送邮件
        return Boolean.TRUE;
    }
}
