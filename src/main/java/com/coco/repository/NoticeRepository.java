package com.coco.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.coco.domain.Notice;


public interface NoticeRepository extends JpaRepository<Notice, Long> {

}
