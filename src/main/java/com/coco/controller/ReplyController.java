package com.coco.controller;

import com.coco.domain.Member;
import com.coco.domain.Reply;
import com.coco.service.MemberService;
import com.coco.service.ReplyService;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReplyController {
    
    private static final Logger log = LoggerFactory.getLogger(ReplyController.class);

    @Autowired
    private ReplyService replyService;

    @Autowired
    private MemberService memberService;

    // 댓글 등록
    @PostMapping("/reply/add")
    public String addReply(@RequestParam Long boardBseq, Principal principal, @RequestParam String content) {
        try {
            String username = principal.getName();
            Member member = memberService.getMemberByUsername(username);

            if (member == null) {
                // 처리할 내용 추가 (예: 에러 페이지로 리다이렉트)
                return "redirect:/error";
            }

            replyService.addReply(boardBseq, member, content);
            return "redirect:/getBoard?bseq=" + boardBseq;
        } catch (UsernameNotFoundException e) {
            log.error("멤버를 찾을 수 없음: {}", e.getMessage());
            throw e; // 예외를 다시 던져서 예외 페이지로 전달
        } catch (Exception e) {
            // 그 외 예외 처리
            log.error("댓글 추가 중 예외 발생", e);
            return "redirect:/error";
        }
    }
    
 // 댓글 삭제
    @PostMapping("/reply/delete/{rseq}")
    public String deleteReply(@PathVariable Long rseq, @RequestParam Long boardBseq, Principal principal) {
        try {
            // 현재 로그인한 사용자 정보 가져오기
            Authentication authentication = (Authentication) principal;
            String username = principal.getName();

            // 댓글 정보 가져오기
            Reply reply = replyService.getReplyById(rseq);

            // 현재 로그인한 사용자의 권한 확인
            if (authentication instanceof UsernamePasswordAuthenticationToken) {
                UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;

                // 댓글의 작성자이거나 어드민이면 삭제 진행
                if (token.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN")) || username.equals(reply.getMember().getUsername())) {
                    replyService.deleteReply(rseq);
                    return "redirect:/getBoard?bseq=" + boardBseq;
                }
            }

            // 권한이 없을 경우 예외 처리 또는 다른 로직 수행
            return "redirect:/accessDenied";
        } catch (Exception e) {
            // 예외 처리
            log.error("댓글 삭제 중 예외 발생", e);
            return "redirect:/error";
        }
    }
    
    // 댓글 수정
    @GetMapping("/reply/edit/{rseq}")
    public String showEditForm(@PathVariable Long rseq, Model model, Principal principal) {
        try {
            // 현재 로그인한 사용자 정보 가져오기
            String username = principal.getName();
            Member loggedInUser = memberService.getMemberByUsername(username);

            // 댓글 정보 가져오기
            Reply reply = replyService.getReplyById(rseq);

            // 현재 로그인한 사용자가 댓글의 작성자이거나 어드민이면 수정 폼 표시
            if (loggedInUser != null && (loggedInUser.getId().equals(reply.getMember().getId()) || hasAdminRole(principal))) {
                model.addAttribute("reply", reply);
                return "editReply"; // 수정 폼을 보여주는 템플릿 이름
            } else {
                // 권한이 없을 경우 예외 처리 또는 다른 로직 수행
                return "redirect:/accessDenied";
            }
        } catch (Exception e) {
            // 예외 처리
            log.error("댓글 수정 폼 표시 중 예외 발생", e);
            return "redirect:/error";
        }
    }

    @PostMapping("/reply/update")
    public String updateReply(@RequestParam Long rseq, @RequestParam String content, @RequestParam Long boardBseq, Principal principal) {
        try {
            // 현재 로그인한 사용자 정보 가져오기
            String username = principal.getName();
            Member loggedInUser = memberService.getMemberByUsername(username);

            // 댓글 정보 가져오기
            Reply reply = replyService.getReplyById(rseq);

            // 현재 로그인한 사용자가 댓글의 작성자이거나 어드민이면 수정 진행
            if (loggedInUser != null && (loggedInUser.getId().equals(reply.getMember().getId()) || hasAdminRole(principal))) {
                replyService.updateReply(rseq, content);
                return "redirect:/getBoard?bseq=" + boardBseq;
            } else {
                // 권한이 없을 경우 예외 처리 또는 다른 로직 수행
                return "redirect:/accessDenied";
            }
        } catch (Exception e) {
            // 예외 처리
            log.error("댓글 수정 중 예외 발생", e);
            return "redirect:/error";
        }
    }

    // 어드민 권한 확인 메소드
    private boolean hasAdminRole(Principal principal) {
        if (principal instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) principal;
            return token.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
        }
        return false;
    }

}