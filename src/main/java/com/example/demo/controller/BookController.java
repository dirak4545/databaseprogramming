package com.example.demo.controller;

import java.net.URI;
import java.util.List;
import java.util.ArrayList;

import com.example.demo.repository.BookRepository;
import com.example.demo.repository.MyBookRepository;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.example.demo.VO.BookVO;
import com.example.demo.VO.MyBookVO;
import com.example.demo.VO.NaverResultVO;

@Controller
public class BookController {
    //도서 정보 데이터베이스 저장
    @Autowired
    private BookService bookService;

    @Autowired
    private MyBookRepository myBookRepository;

    @Autowired
    private BookRepository bookRepository;

    //도서 검색
    @GetMapping("searchlist")
    public String list(String text, Model model) {

        // 네이버 검색 API 요청
        String clientId = "COm_5Cf4OB4vQmqFDYcb";
        String clientSecret = "07G9iZtOBi";

        //String apiURL = "https://openapi.naver.com/v1/search/blog?query=" + text;    // JSON 결과
        URI uri = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com")
                .path("/v1/search/book.json")
                .queryParam("query", text)
                .queryParam("display", 10)
                .queryParam("start", 1)
                .queryParam("sort", "sim")
                .encode()
                .build()
                .toUri();

        // Spring 요청 제공 클래스
        RequestEntity<Void> req = RequestEntity
                .get(uri)
                .header("X-Naver-Client-Id", clientId)
                .header("X-Naver-Client-Secret", clientSecret)
                .build();
        // Spring 제공 restTemplate
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> resp = restTemplate.exchange(req, String.class);

        // JSON 파싱 (Json 문자열을 객체로 만듦, 문서화)
        ObjectMapper om = new ObjectMapper();
        NaverResultVO resultVO = null;

        try {
            resultVO = om.readValue(resp.getBody(), NaverResultVO.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        List<BookVO> books = resultVO.getItems();

        // 각 BookVO 객체에 id 설정하는 부분 추가
        for (BookVO book : books) {
            if (book.getId() == null) {
                System.out.println("Book title: " + book.getTitle() + ", Current ID: " + book.getId());
            }
        }
        // books를 list.html에 출력 -> model 선언
        model.addAttribute("books", books);
        model.addAttribute("text", text);

        bookService.saveBooks(books);

        return "searchBook";
    }
//        도서 찜하기
    @PostMapping("/saveBook")
    public String saveBook(@RequestParam Long bookid, @RequestParam String bookname) {
        bookService.saveToMyBook(bookid, bookname);
        return "myBookList";  // 찜 목록 페이지로 리다이렉트
    }

//    @GetMapping("/mybooks")
//    public String myBookList(Model model) {
//        List<MyBookVO> myBooks = bookService.getMyBooks();
//        model.addAttribute("myBooks", myBooks);
//        return "myBookList";  // 찜 목록을 보여주는 뷰
//    }

    @GetMapping("/booklist2")
    public String bookList(Model model) {
        List<BookVO> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "booklist2";
    }

//    // 찜하기 버튼 처리
//    @PostMapping("/favorite")
//    public String favoriteBook(@RequestParam(required = true) Long id) {  // required = true로 변경
//        try {
//            boolean isFavorite = bookService.toggleFavorite(id);
//            return "redirect:/searchlist";
//        } catch (IllegalArgumentException e) {
//            return "redirect:/booklist2";
//        }
//    }
    @GetMapping("/mybook-list")
    public String myBookList(Model model) {
        List<MyBookVO> mybooks = bookService.getMyBooks();
        model.addAttribute("mybooks", mybooks);
        return "myBookList";
    }
}