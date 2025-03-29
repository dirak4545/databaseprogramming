package com.example.demo.repository;

import com.example.demo.VO.BookVO;
import jakarta.transaction.Transactional;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<BookVO, Long> {
    boolean existsByIsbn(String isbn);  // ISBN으로 중복 여부 체크

    // 책을 ISBN이나 ID로 찾는 메서드 (예시)
    Optional<BookVO> findById(Long id);

    // 찜 상태를 업데이트하는 메서드
    @Modifying
    @Transactional
    @Query("UPDATE BookVO b SET b.isFavorite = :isFavorite WHERE b.id = :id")
    void updateFavoriteStatus(@Param("id") Long id, @Param("isFavorite") boolean isFavorite);

}