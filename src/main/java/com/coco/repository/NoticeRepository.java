package com.coco.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coco.domain.Notice;


public interface NoticeRepository extends JpaRepository<Notice, Long> {


}
