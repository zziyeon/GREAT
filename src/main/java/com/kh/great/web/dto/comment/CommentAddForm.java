package com.kh.great.web.dto.comment;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CommentAddForm {
  private Long articleNum;          //  article_num          number(6),  -- 게시글 번호
  private Long commentGroup;        //  comment_group        number(6),   -- 댓글 그룹
//  private Long commentNum;          //  comment_num          number(6),  -- 댓글 번호
  private Long pCommentNum;         //  p_comment_num        number(6),  -- 부모 댓글 번호
  private Long step;                // 댓글 단계
  private Long commentOrder;        // 댓글 순서
  private String pCommentNickname;  // 부모 댓글 닉네임
  private Long memNumber;           //  mem_number           number(6),  -- 회원 번호
  @Length(min = 1, max = 200)
  private String commentContents;   //  comment_contents     clob,       -- 댓글 내용
  private String reply;             // 답글 여부
//  private LocalDateTime createDate; //  create_date          date,       -- 댓글 생성일
//  private Member member;
  private MultipartFile file;        //이미지 첨부 : 단건
}
