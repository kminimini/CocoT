package com.coco.repository;

import com.coco.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

	List<Reply> findByBoardBseq(Long boardBseq);
	
	@Modifying
    @Query("DELETE FROM Reply r WHERE r.parentReply.id = :replyId")
    void deleteSubReplies(@Param("replyId") Long replyId);
	
	@Modifying
	@Transactional
    @Query("DELETE FROM Reply r WHERE r.id = :replyId OR r.parentReply.id = :replyId")
    void deleteReplyAndSubReplies(@Param("replyId") Long replyId);

    // 추가적인 댓글과 관련된 쿼리 메서드가 필요하다면 여기에 추가할 수 있습니다.

}
