package com.kh.great.domain.dao.notice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notice{

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "notice_id")
  private Long noticeId;           // --공지사항게시글 번호
  @Length(min=1, max=20)
  private String title;            //--게시글 제목
  private String content;          //--게시글 내용
  private String attachments;        //첨부파일 유무
  private String write;           //--작성자(관리자)
  private Long count;             //조회수
  @DateTimeFormat(pattern = "yyyy.MM.dd.")
  private LocalDateTime udate;   //게시글 작성일(수정일)

}

