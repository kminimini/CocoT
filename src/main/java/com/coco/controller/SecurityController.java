package com.coco.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.coco.domain.Member;
import com.coco.dto.JoinFormDto;
import com.coco.dto.MailDto;
import com.coco.service.MemberService;
import com.coco.service.SendEmailService;

@Controller
@RequestMapping("/system")
@SessionAttributes("user")
public class SecurityController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private SendEmailService sendEmailService;

	/* TODO 로그인 화면 이동 */
	@GetMapping("/login")
	public void showLoginPage() {

	}

	@PostMapping("/login")
	public String loginAction(@RequestParam String username, @RequestParam String password, RedirectAttributes redirectAttributes, Model model) {
	    Optional<Member> findMember = memberService.getMemberByEmail(username);

	    if (findMember.isPresent() && passwordMatches(password, findMember.get().getPassword())) {
	        // 로그인 성공
	        return "redirect:/index";
	    } else {
	        // 로그인 실패
	        model.addAttribute("error", "이메일이나 비밀번호가 틀렸습니다.");
	        return "redirect:/system/login";
	    }
	}

	private boolean passwordMatches(String inputPassword, String storedPassword) {
	    // 여기에서 입력된 비밀번호와 저장된 비밀번호를 비교하는 로직을 추가하세요.
	    // 이전에 설명드린 것처럼, Spring Security의 PasswordEncoder를 사용하는 것이 좋습니다.
	    // 예시: return passwordEncoder.matches(inputPassword, storedPassword);
		return passwordMatches(inputPassword, storedPassword);
	}

	/* TODO 회원가입 화면 이동 */
	@GetMapping("/join")
	public void showJoinForm(Model model, @ModelAttribute JoinFormDto joinFormDto) {
		
	}
	
	 // AJAX를 통한 아이디 중복 확인
	@PostMapping("/checkIdAvailability")
	@ResponseBody
	public String checkIdAvailability(@RequestParam String id) {
	    int count = memberService.findidCheck(id);
	    return (count > 0) ? "no_use" : "useable";
	}
	
	/* TODO 회원가입 */
	@PostMapping("/join")
	public String registerUser(@ModelAttribute("joinFormDto") JoinFormDto joinFormDto, BindingResult bindingResult) {
		// joinFormDto를 이용한 회원 가입 처리
		String memberId = memberService.entitySave(joinFormDto);

		// 등록 성공 후 로그인 페이지로 리디렉션
		return "redirect:/system/login";
	}
	
	// 비밀번호 찾기 화면 표시
	@GetMapping("/findPwdView")
	public void findPwdView() {

	}

	// 비밀번호 찾기 처리
	@PostMapping("/findPwdView")
	@ResponseBody
	public String sendEmail(String email, String id, Model model) {
		Member member = memberService.getMemberByIdAndEmail(id, email);
		String result = "";
		
		if (member != null) {
			System.out.println("sendEmail() : member" + member);
			// 회원을 찾으면 이메일 생성 및 전송
			MailDto dto = sendEmailService.createMailAndChangePassword(member.getEmail(), member.getId());
			sendEmailService.mailSend(dto);

			result = "success";
		} else {
			// 회원을 찾지 못하면 오류 메시지 반환
			System.out.println("id 찾기 실패...");
			result = "fail";
		}
		
		return result;
	}

	@PostMapping("/logout")
    public String logout(SessionStatus status) {
        status.setComplete();
        return "redirect:/"; // 로그아웃 후에 리다이렉트할 경로를 설정
    }
}
