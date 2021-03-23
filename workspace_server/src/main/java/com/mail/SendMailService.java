package com.mail;

import com.AllApplication;
import com.pojo.EmailInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import freemarker.template.Configuration;
import freemarker.template.Template;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.StringWriter;

@Component
public class SendMailService {
    @Autowired
    JavaMailSender javaMailSender;


    private String sendEmail="15599036120@163.com";
    public void sendEmail(String to, String subject, String content)
    {
        try{
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8");
            helper.setTo(to);
            helper.setFrom(sendEmail);
            helper.setSubject(subject);
            helper.setText(content,true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            System.out.println("发送失败");
        }
    }

    public void sendHtmlMail(String subject,String verificationCode){

        try{
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_0);
            ClassLoader loader = AllApplication.class.getClassLoader();
            configuration.setClassLoaderForTemplateLoading(loader,"ftl");
            Template template = configuration.getTemplate("sendVerificationCode.ftl");
            StringWriter mail = new StringWriter();
            EmailInfo emailInfo = new EmailInfo(subject,verificationCode);
            template.process(emailInfo,mail);
            sendEmail("1419561484@qq.com","找回密码",
                    mail.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
