package com.coco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.coco.dto.MailDto;

@Service
public class MailService {

	 @Autowired
	 private JavaMailSender javaMailSender;

  
//     public void sendEmail(MailDto mailDto) {
//    	 SimpleMailMessage message = new SimpleMailMessage();
//    	 message.setSubject(mailDto.getTitle());
//         message.setTo(mailDto.getRecipient());
//         message.setText(mailDto.getContent());
//
//    	 
//    	 javaMailSender.send(message);
//    	 
//     }
//     
}

