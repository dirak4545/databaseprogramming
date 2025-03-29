package com.example.demo.VO;

import lombok.*;

import java.util.List;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class NaverResultVO {
    private String lastBuildDate;
    private int total;
    private  int start;
    private int display;
    private List<BookVO> items;
}
