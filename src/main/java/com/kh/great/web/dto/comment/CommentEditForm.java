package com.kh.great.web.dto.comment;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CommentEditForm {
//  private Long articleNum;          //  article_num          number(6),  -- 게시글 번호
//  private Long commentGroup;        //  comment_group        number(6),   -- 댓글 그룹
  private Long commentNum;          //  comment_num          number(6),  -- 댓글 번호
//  private Long pCommentNum;         //  p_comment_num        number(6),  -- 부모 댓글 번호
//  private Long memNumber;           //  mem_number           number(6),  -- 회원 번호
  private String commentContents;   //  comment_contents     clob,       -- 댓글 내용
//  private LocalDateTime createDate; //  create_date          date,       -- 댓글 생성일
//  private Long commentIndent;       //  comment_indent       number(3)  -- 대댓글 들여쓰기
//  private Member member;
  private MultipartFile file;
//  private UploadFile attachFile;
}
