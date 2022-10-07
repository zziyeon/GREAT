package com.kh.great.domain.dao.article;

import java.util.List;
import java.util.Optional;

public interface ArticleDAO {
  /**
   * 게시글 목록 조회1 : 전체
   *
   * @return 게시글 리스트
   */
  List<Article> findAll();

  /**
   * 게시글 목록 조회2 : 카테고리
   *
   * @param category 카테고리
   * @return 게시글 리스트
   */
  List<Article> findAll(String category);

  /**
   * 게시글 목록 조회3 : 페이지
   *
   * @param startRec 첫 페이지
   * @param endRec   마지막 페이지
   * @return 게시글 리스트
   */
  List<Article> findAll(int startRec, int endRec);

  /**
   * 게시글 목록 조회4 : 카테고리, 페이지
   *
   * @param category 카테고리
   * @param startRec 첫 페이지
   * @param endRec   마지막 페이지
   * @return 게시글 리스트
   */
  List<Article> findAll(String category, int startRec, int endRec);

  /**
   * 게시글 목록 조회5 : 검색
   *
   * @param filterCondition 분류, 시작 레코드 번호, 종료 레코드 번호, 검색 유형, 검색어
   * @return
   */
  List<Article> findAll(ArticleFilterCondition filterCondition);

  /**
   * 게시글 조회
   *
   * @param articleNum 게시글 번호
   * @return 게시글
   */
  Optional<Article> read(Long articleNum);


  /**
   * 게시글 작성
   *
   * @param article 게시글 작성 내용
   * @return 게시글
   */
  int save(Article article);

  /**
   * 게시글 수정
   *
   * @param articleNum 게시글 번호
   * @param article    게시글 수정 내용
   */
  int update(Long articleNum, Article article);

  /**
   * 게시글 삭제
   */
  int delete(Long articleNum);

  /**
   * 신규 게시물 번호 생성
   * @return 게시글 번호
   */
  Long generatedArticleNum();

  /**
   * 조회수 증가
   * @param articleNum 게시글 번호
   * @return 수정건수
   */
  int increaseViewCount(Long articleNum);

  /**
   * 게시물의 댓글 수 업데이트
   * @param articleNum 게시물 번호
   * @param totalCountOfArticle 댓글 수
   * @return 수정건수
   */
  int updateCommentsCnt(Long totalCountOfArticle, Long articleNum);

  /**
   * 전체 건수
   * @return 게시글 전체 건수
   */
  int totalCount();

  /**
   * 전체 건수
   * @param category 게시글 유형
   * @return 게시글 전체 건수
   */
  int totalCount(String category);

  /**
   * 전체 건수
   * @param filterCondition 필터 조건
   * @return 게시글 전체 건수
   */
  int totalCount(ArticleFilterCondition filterCondition);
}
