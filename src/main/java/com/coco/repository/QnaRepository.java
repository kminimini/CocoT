package com.coco.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coco.domain.Qna;


public interface QnaRepository extends JpaRepository<Qna, Long> {

}
