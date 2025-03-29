package com.example.demo.VO;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "mybooks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MyBookVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long bookid;  // 원본 도서의 ID

    @Column(length = 500)
    private String bookname;  // 도서명

    private boolean favorite = true;  // 찜 상태

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}