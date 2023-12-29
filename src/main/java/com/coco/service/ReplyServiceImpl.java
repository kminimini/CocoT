package com.coco.service;

import com.coco.domain.Board;
import com.coco.domain.Member;
import com.coco.domain.Reply;
import com.coco.repository.MemberRepository;
import com.coco.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class ReplyServiceImpl implements ReplyService {

    @Autowired
    private ReplyRepository replyRepository;
    
    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    // 댓글 등록
    @Override
    public void addReply(Long boardBseq, Member member, String content) {
        // 댓글 추가 로직 구현
        // boardBseq와 member, content를 활용하여 적절한 처리 수행

        Board board = new Board();
        board.setBseq(boardBseq);

        // Reply 엔티티에 사용자 이름 설정
        Reply reply = Reply.builder()
                .board(board)
                .member(member)
                .rcontent(content)
                .username(member.getUsername()) // 사용자 이름을 엔티티에서 가져오도록 수정
                .build();

        replyRepository.save(reply);
    }
    
    @Override
    public void addSubReply(Long parentReplyId, Reply subReply) {
        Optional<Reply> optionalParentReply = replyRepository.findById(parentReplyId);

        if (optionalParentReply.isPresent()) {
            Reply parentReply = optionalParentReply.get();
            subReply.setParentReply(parentReply);
            parentReply.getChildren().add(subReply);

            replyRepository.save(subReply);
        } else {
            System.out.println("부모 댓글이 없습니다. parentReplyId: " + parentReplyId);
        }
    }


    
    @Override
    public List<Reply> getReplies(Long boardBseq) {
        return replyRepository.findByBoardBseq(boardBseq);
    }
    
    // 댓글 삭제
    @Override
	public void deleteReply(Long replyId) {
    	// 댓글 및 대댓글 삭제
        replyRepository.deleteReplyAndSubReplies(replyId);
		
	}
    
    @Override
    public void deleteReplyAndSubReplies(Long replyId) {
        replyRepository.deleteSubReplies(replyId);
        replyRepository.deleteById(replyId);
    }
    
    // 댓글 수정
    @Override
    public void updateReply(Long rseq, String content) {
        // 댓글 엔티티 조회
        Optional<Reply> optionalReply = replyRepository.findById(rseq);

        if (optionalReply.isPresent()) {
            // 댓글 내용 업데이트
            Reply reply = optionalReply.get();
            reply.setRcontent(content);

            // 댓글 저장
            replyRepository.save(reply);
        } else {
            // 댓글이 없을 경우에 대한 처리
            // 여기에서는 간단히 댓글이 없다는 로그를 출력합니다.
            System.out.println("댓글이 없습니다. rseq: " + rseq);
            // 또는 원하는 다른 동작을 수행할 수 있습니다.
        }
    }
    
    @Override
    public Reply getReplyById(Long rseq) {
        return replyRepository.findById(rseq).orElse(null);
    }

}