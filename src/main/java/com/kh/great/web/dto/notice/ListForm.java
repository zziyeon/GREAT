package com.kh.great.web.dto.notice;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class ListForm {
  private Long noticeId;           // --공지사항게시글 번호
  @Length(min = 1,max = 50)
  private String title;            //--게시글 제목
  private String write;           //--작성자(관리자)
  private String attachments;
  @DateTimeFormat(pattern = "yyyy.MM.dd.")
  private LocalDateTime udate;
  private Long count;             //조회수

}
