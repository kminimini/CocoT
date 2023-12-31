package com.coco.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.coco.domain.Member;
import com.coco.security.SecurityUser;
import com.coco.service.MemberService;

@Controller
public class MyPageController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger log = LoggerFactory.getLogger(MyPageController.class);
    
    // 마이페이지
    @GetMapping("/myPage")
    public String myPage(@AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : member") Object principal, Model model) {
        // 사용자가 로그인하지 않은 경우
        if (principal == null) {
            return "redirect:/system/login";
        }

        Member currentMember = memberService.findById(((Member) principal).getMid());
        model.addAttribute("currentMember", currentMember);
        return "myPage";
    }
      
    // 비밀번호 유효성 검사 핸들러
    @PostMapping("/myPage/changePassword")
    @ResponseBody
    public String validatePassword(
            @AuthenticationPrincipal SecurityUser securityUser,
            @RequestParam String currentPassword,
            @RequestParam String newPassword) {
    	
    	System.out.println("currentPassword=" + currentPassword);
    	System.out.println("newPassword=" +newPassword);
        // 현재 사용자의 실제 비밀번호 
    	Member currentUser = securityUser.getMember();

    	Member member = memberService.getMember(currentUser.getEmail());
    	String userCurrentPassword = member.getPassword();
        
        // 입력한 현재 비밀번호가 실제 비밀번호와 일치하는지 여부 확인
    	boolean matches = passwordEncoder.matches(currentPassword, userCurrentPassword);
        if (matches) {
        	boolean result = memberService.changePassword(currentPassword, newPassword);
        	
        	
        	if (result) {
        		System.out.println("success");
        		return "success";
        	} else {
        		System.out.println("failure");
        		return "failure";
        	}
        } else {
        	// 현재 비밀번호가 일치하지 않으면 "failure" 반환
        	System.out.println("failure2");
            return "failure";
        }
        
        
    }

    // 회원탈퇴
    @PostMapping("/myPage/deleteAccount")
    @ResponseBody
    public String deleteAccount(
            @AuthenticationPrincipal SecurityUser securityUser,
            @RequestParam String withdrawPassword,
            HttpServletRequest request) {

        Member currentUser = securityUser.getMember();
        Member member = memberService.getMember(currentUser.getEmail());
        String userCurrentPassword = member.getPassword();

        boolean matches = passwordEncoder.matches(withdrawPassword, userCurrentPassword);

        if (matches) {
            try {
                // 회원 삭제
                memberService.deleteMemberById(currentUser.getMid());

                // 세션 무효화
                HttpSession session = request.getSession(false);
                if (session != null) {
                    session.invalidate();
                }

                // SecurityContext 비우기
                SecurityContextHolder.clearContext();

                System.out.println("탈퇴 성공");
                return "success";
            } catch (Exception e) {
                System.out.println("탈퇴 실패: " + e.getMessage());
                return "failure";
            }
        } else {
            System.out.println("현재 비밀번호 불일치");
            return "failure";
        }
    }
}

