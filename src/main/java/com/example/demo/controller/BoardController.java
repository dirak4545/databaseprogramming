package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.dto.BoardDTO;
import com.example.demo.service.BoardService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BoardController {

	private final BoardService boardService;
	
	@GetMapping("/list")
	public String getList(Model model) {
		List<BoardDTO> bookList = boardService.getList();
		model.addAttribute("bookList", bookList);
		
		return "bookList";
	}
	
//	도서 추가
	@GetMapping("addBook")
	public String addBook() {
		System.out.println("도서 추가 페이지 연결 성공 !!");
		return "addBook";
	}
	
	// 도서 추가(DB 연동)
	@PostMapping("addBook")
	public void save(BoardDTO boardDTO) {
		boardService.save(boardDTO);	
	}
	//도서 상세정보 가져오기
	@GetMapping("/{id:[0-9]+}")
	public String detail(@PathVariable("id")Integer id, Model model) {
		BoardDTO boardDTO = boardService.detail(id);
		model.addAttribute("bookDetail",boardDTO);
		return "detailBook";
	}
//	도서 목록 삭제
	@GetMapping("/goDelete/{id}")
	public String goDelete(@PathVariable("id")Integer id) {
		boardService.goDelete(id);
		return "redirect:/list";
		
	}
	@GetMapping("/goUpdate/{id}")
	public String goUpdate(@PathVariable("id")Integer id, Model model) {
		BoardDTO boardDTO = boardService.detail(id);
		model.addAttribute("bookDetail",boardDTO);
		return "updateBook";
	}

	@PostMapping("/goUpdate/{id}")
	public String goUpdate(BoardDTO boardDTO, Model model) {
		boardService.goUpdate(boardDTO);
		BoardDTO dto = boardService.detail(boardDTO.getBookid());

		model.addAttribute("bookDetail",dto);
		return "detailBook";
	}
}
