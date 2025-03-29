package com.example.demo.service;

import com.example.demo.VO.BookVO;
import com.example.demo.VO.MyBookVO;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.MyBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private MyBookRepository myBookRepository;

    @Transactional(readOnly = true)
    public List<BookVO> getAllBooks() {
        return bookRepository.findAll();
    }

    @Transactional
    public void deleteBook(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Book ID must not be null");
        }
        bookRepository.deleteById(id);
    }
    @Transactional
    public void saveBooks(List<BookVO> books) {
        if (books == null || books.isEmpty()) {
            throw new IllegalArgumentException("Book list must not be null or empty");
        }

        for (BookVO book : books) {
            // 중복된 ISBN 확인 (책의 고유 ISBN을 기준으로 중복 여부 확인)
            boolean exists = bookRepository.existsByIsbn(book.getIsbn());

            // 중복된 책이 없을 경우만 저장
            if (!exists) {
                bookRepository.save(book);
            }
        }
    }

    // 찜 상태 토글
    public boolean toggleFavorite(Long bookId) {
        // 책을 찾아서
        BookVO book = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Invalid book ID"));

        // 현재 찜 상태를 반전시켜서 업데이트
        boolean newFavoriteStatus = !book.isFavorite();
        bookRepository.updateFavoriteStatus(bookId, newFavoriteStatus);

        return newFavoriteStatus; // 새로운 찜 상태 반환
    }
    @Transactional
    public void saveToMyBook(Long bookid, String bookname) {
        // 이미 찜한 책인지 확인
        if (myBookRepository.existsByBookid(bookid)) {
            // 이미 찜한 책이면 삭제 (찜 해제)
            myBookRepository.deleteByBookid(bookid);
        } else {
            // 새로운 찜하기
            MyBookVO myBook = new MyBookVO();
            myBook.setBookid(bookid);
            myBook.setBookname(bookname);
            myBookRepository.save(myBook);
        }
    }

    // 찜한 책 목록 조회
    public List<MyBookVO> getMyBooks() {
        return myBookRepository.findAll();
    }
}