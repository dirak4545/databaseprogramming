package com.example.demo.repository;

import com.example.demo.VO.MyBookVO;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MyBookRepository extends JpaRepository<MyBookVO, Long> {
    boolean existsByBookid(Long bookid);
    void deleteByBookid(Long bookid);
    List<MyBookVO> findAllByOrderByCreatedAtDesc();
}