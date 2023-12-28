package com.coco.repository;

import com.coco.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

	List<Reply> findByBoardBseq(Long boardBseq);

    // 추가적인 댓글과 관련된 쿼리 메서드가 필요하다면 여기에 추가할 수 있습니다.

}
