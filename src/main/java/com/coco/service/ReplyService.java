package com.coco.service;

import com.coco.domain.Member;
import com.coco.domain.Reply;

import java.security.Principal;
import java.util.List;

public interface ReplyService {

	void addReply(Long boardBseq, Member member, String content);
	
	void addSubReply(Long parentReplyId, Reply subReply);

    List<Reply> getReplies(Long boardBseq);
    
    void deleteReply(Long replyId);
    
    void deleteReplyAndSubReplies(Long replyId);
    
    void updateReply(Long rseq, String content);
    
    Reply getReplyById(Long rseq);

}

