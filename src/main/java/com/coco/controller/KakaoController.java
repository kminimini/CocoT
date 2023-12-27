package com.coco.controller;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.client.RestTemplate;

import com.coco.service.KakaoService;

import lombok.RequiredArgsConstructor;

@SessionAttributes(value="access_token")
@Controller
@RequiredArgsConstructor
public class KakaoController {

	private final KakaoService kakaoService;
	
	  private final RestTemplate restTemplate;
	  private final HttpSession httpSession;

	  @RequestMapping(value="kakaologin")
	public String kakaologin(Model model) {
		model.addAttribute("kakaoUrl", kakaoService.getKakaoLogin());
		
		return "/system/login.html";
	}

	@RequestMapping("system/login/auth/kakao/callback")
	public String kakaoCallback(@RequestParam("code") String code, HttpSession session, Model model) throws Exception {
		String accessToken = kakaoService.getAccessToken(code);
		System.out.println("kakaoCallback() : access_token = " + accessToken);
		HashMap<String,Object> userInfo = kakaoService.getUserInfo(accessToken);
		
		session.setAttribute("email", userInfo.get("email"));
		session.setAttribute("nickname", userInfo.get("nickname"));
		session.setAttribute("id", userInfo.get("id"));
		System.out.println(userInfo);

		// 로그인 성공 시 처리
		model.addAttribute("access_token", accessToken);
		session.setAttribute("access_token", accessToken);
		String token = (String)session.getAttribute("access_token");
		System.out.println("After session save : " + accessToken);

		return "redirect:/index";
	}
	
	@RequestMapping("/system/logout/kakao")
	public String logout(SessionStatus status, HttpSession session) throws Exception {
//		String kakaoLogoutUrl = "https://kapi.kakao.com/v1/user/logout";
		String accessToken = (String)session.getAttribute("access_token");
		
		System.out.println("logout() : access_token=" + accessToken);
	
		kakaoService.logout(accessToken);

		session.removeAttribute("access_token");
		
		status.setComplete();
		
        return "redirect:/"; // 로그아웃 후에 리다이렉트할 경로를 설정
	}
	
	@RequestMapping(value="/kakaounlink")
	public String unlink(HttpSession session) {
		kakaoService.unlink((String)session.getAttribute("access_token"));
		session.invalidate();
		
		return "redirect:/";
	}
}
