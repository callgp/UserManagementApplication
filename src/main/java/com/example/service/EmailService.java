package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;
	public boolean sendEmail(String subject, String body, String to) {
		
		/*
		 * try { MimeMessage mimeMessage = mailSender.createMimeMessage();
		 * MimeMessageHelper helper=new MimeMessageHelper(mimeMessage);
		 * 
		 * helper.setSubject(subject); helper.setText(body); helper.setTo(to);
		 * mailSender.send(mimeMessage);
		 * 
		 * }catch(Exception e) { e.printStackTrace(); } return true;
		 */
		
		
		SimpleMailMessage message = new SimpleMailMessage();
    //    message.setFrom("your-email@gmail.com"); // Replace with your sender email
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
        return true;
        
	}
}
