package com.coco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.coco.domain.Member;
import com.coco.dto.MailDto;
import com.coco.repository.MemberRepository;

import lombok.AllArgsConstructor;

@Service
public class SendEmailService{

	  @Autowired
	    private MemberRepository memberRepository;

	    @Autowired
	    private JavaMailSender mailSender;


	    public MailDto createMailAndChangePassword(String email, String id) {
	        String str = getTempPassword();
	        MailDto dto = new MailDto();
	        dto.setRecipient(email);
	        dto.setTitle(id + "님의 coco항공 임시비밀번호 안내 이메일 입니다.");
	        dto.setContent("안녕하세요. coco항공 임시비밀번호 안내 관련 이메일 입니다." + "[" + id + "]" + "님의 임시 비밀번호는 " + str + " 입니다.");
	        updatePassword(email, str);
	        return dto;
	    }

	    public void updatePassword(String email, String newPassword) {
	    	 Member member = memberRepository.getMemberByEmail(email);

	         if (member != null) {
	             // Update the user's password
	             memberRepository.updateUserPassword(member.getId(), newPassword);
	         }
	    }

	    public void mailSend(MailDto mailDto) {
	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setTo(mailDto.getRecipient());
	        message.setSubject(mailDto.getTitle());
	        message.setText(mailDto.getContent());

	        mailSender.send(message);
	    }

	    public String getTempPassword() {
	        char[] charSet = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
	                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

	        StringBuilder str = new StringBuilder();

	        int idx;
	        for (int i = 0; i < 10; i++) {
	            idx = (int) (charSet.length * Math.random());
	            str.append(charSet[idx]);
	        }
	        return str.toString();
	    }
	}