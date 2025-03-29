package com.example.demo.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String title;
    private String link;
    private String author;
    private String price;
    private BigDecimal discount;
    private String publisher;
    private LocalDate pubdate;
    private String isbn;
    private String description;
    private String image;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice() {
        return price.toString();
    }

    // 추가된 컬럼
    @Column(name = "is_favorite", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isFavorite;

    // Getter, Setter
    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
