package com.kh.great.web.dto.article;

import com.kh.great.domain.dao.member.Member;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class BoardForm {
  private Long articleNum;               //  article_num           number(6),    게시글 번호
  private Long memNumber;                //  mem_number            number(6),    회원 번호
  private String articleCategory;        //  article_category      varchar2(6),  게시글 종류
  @Length(min=1, max=50)
  private String articleTitle;           //  article_title         varchar2(90), 게시글 제목
  @NotBlank
  private String articleContents;        //  article_contents      clob,         게시글 내용 string?
  private String attachment;             //  attachment            varchar2(1),  첨부파일 유무
  @DateTimeFormat(pattern = "yyyy.MM.dd.")
  private LocalDateTime createDate;      //  create_date           date,         작성일
  private Long views;                    //  views                 number(5)     조회수
  private Long comments;                 //  comments              number(5)     댓글수
  private Member member;
}
