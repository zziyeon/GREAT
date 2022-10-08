package com.kh.great.web.dto.comment;

import com.kh.great.domain.dao.member.Member;
import com.kh.great.domain.dao.uploadFile.UploadFile;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentListForm {
  private Long articleNum;          //  article_num          number(6),  -- 게시글 번호
  private Long commentGroup;        //  comment_group        number(6),   -- 댓글 그룹
  private Long commentNum;          //  comment_num          number(6),  -- 댓글 번호
  private Long pCommentNum;         //  p_comment_num        number(6),  -- 부모 댓글 번호
  private Long memNumber;           //  mem_number           number(6),  -- 회원 번호
  private String commentContents;   //  comment_contents     clob,       -- 댓글 내용
  private LocalDateTime createDate; //  create_date          date,       -- 댓글 생성일
  private Member member;
  private UploadFile attachFile;
}
