package com.kh.great.domain.dao.comment;

import java.util.List;
import java.util.Optional;

public interface CommentDAO {

  /**
   * 댓글 조회
   * @param commentNum 댓글 번호
   * @return 댓글
   */
  Optional<Comment> find(Long commentNum);

  /**
   * 게시글에 달린 댓글 목록 조회
   * @return 댓글 목록
   */
  List<Comment> findAll(Long articleNum);

  /**
   * 댓글 작성
   * @param comment 댓글 정보
   * @return 작성된 댓글 수
   */
  int save(Comment comment);

  /**
   * 댓글 수정
   * @param commentNum 댓글 번호
   * @param comment 댓글 내용
   * @return
   */
  int update(Long commentNum, Comment comment);

  /**
   * 댓글 삭제
   * @param commentNum 댓글 번호
   * @return 댓글 내용
   */
  int delete(Long commentNum);

  /**
   * 신규 댓글 번호 생성
   * @return 댓글 번호
   */
  Long generatedCommentNum();

  /**
   * 신규 댓글 그룹 번호 생성
   * @return 댓글 그룹 번호
   */
  Long generatedCommentGroupNum();

  /**
   * 게시물 댓글 건수 조회
   * @param articleNum 게시글 번호
   * @return 댓글 건수
   */
  int totalCountOfArticle(Long articleNum);

}
