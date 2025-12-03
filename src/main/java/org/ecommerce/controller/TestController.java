package org.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {
    
    @Autowired
    private JavaMailSender mailSender;
    
    @GetMapping("/email")
    public String testEmail() {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("maheshkulkarni9797@gmail.com");
            message.setTo("maheshkulkarni9797@gmail.com"); // Send to yourself
            message.setSubject("Test Email");
            message.setText("This is a test email from Spring Boot");
            
            mailSender.send(message);
            return "Email sent successfully!";
            
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed: " + e.getMessage();
        }
    }
}