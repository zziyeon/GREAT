package com.kh.great.web.dto.article;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class ArticleAddForm {
  private Long memNumber;                //  mem_number            number(6),    회원 번호
  private String articleCategory;        //  article_category      varchar2(6),  게시글 종류
  @Length(min = 1, max = 50)
  private String articleTitle;           //  article_title         varchar2(90), 게시글 제목
  @NotBlank
  private String articleContents;        //  article_contents      clob,         게시글 내용
  private String attachment;             //  attachment            varchar2(1),  첨부파일 유무

  //private MultipartFile file;        //상품 이미지 첨부 : 단건
  //private List<MultipartFile> files; //상품 이미지 첨부 : 여러건
}
