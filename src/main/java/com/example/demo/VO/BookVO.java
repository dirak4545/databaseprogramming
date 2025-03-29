package com.example.demo.VO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "books")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BookVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500)
    private String title;

    @Column(length = 1000)
    private String link;

    @Column(length = 1000)
    private String image;

    @Column(length = 200)
    private String author;

    private String discount;

    @Column(length = 100)
    private String publisher;

    private String pubdate;

    @Column(unique = true)
    private String isbn;

    @Column(length = 1000)
    private String description;

    private Double price;

    @Column(name = "is_favorite")
    private boolean isFavorite;  // 찜하기를 표현하는 boolean 컬럼
}