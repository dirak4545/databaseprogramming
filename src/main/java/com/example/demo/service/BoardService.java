package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.BoardDTO;
import com.example.demo.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
	
	private final BoardRepository boardRepository;
	
//	도서목록 조회
	public List<BoardDTO> getList(){
		return boardRepository.getList();
	}
//  도서 추가(DB 연동)
	public void save(BoardDTO boardDTO) {
		// TODO Auto-generated method stub
		boardRepository.save(boardDTO);
		
	}
//	도서 상세정보 가져오기
	public BoardDTO detail(int id) {
		// TODO Auto-generated method stub
		return boardRepository.detail(id);
	}
//	도서 목록 삭제 
	public void goDelete(Integer id) {
		// TODO Auto-generated method stub
		boardRepository.goDelete(id);
	}
	public void goUpdate(BoardDTO boardDTO) {
		boardRepository.goUpdate(boardDTO);
	}

}
