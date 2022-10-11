package com.kh.great.web.dto.notice;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class DetailForm {
  private Long noticeId;             //공시사항번호
  @Length(min = 1,max = 50)
  private String title;             //제목
  private String content;           //내용
  private String attachments;
  private String write;       //작성자
  @DateTimeFormat(pattern = "yyyy.MM.dd.HH.mm")
  private LocalDateTime udate;
}