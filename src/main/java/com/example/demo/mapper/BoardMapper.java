package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.example.demo.dto.BoardDTO;

@Mapper
public interface BoardMapper {
    void saveBook(BoardDTO boardDTO);
}
