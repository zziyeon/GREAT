package com.kh.great.domain.svc.notice;

import com.kh.great.domain.dao.notice.BbsFilterCondition;
import com.kh.great.domain.dao.notice.Notice;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface NoticeSVC {

  /**
   * 목록
   * @return
   */
  List<Notice> foundAll();
  List<Notice>  findAll(int startRec, int endRec);

  /**
   * 검색
   * @param filterCondition 분류,시작레코드번호,종료레코드번호,검색유형,검색어
   * @return
   */
  List<Notice>  findAll(BbsFilterCondition filterCondition);

  //등록
  Notice write(Notice notice);

  //전체조회
  List<Notice> findAll();

  //상세
  Notice read(Long noticeId);

  /**
   * 게시글 수정
   *
   * @param noticeId 게시글 번호
   * @param notice    게시글 수정 내용
   */
  Notice update(Long noticeId, Notice notice);

  Notice update(Long noticeId, Notice notice, List<MultipartFile> files);

  //삭제
  void delete(Long noticeId);

  /**
   * 전체건수
   * @return 게시글 전체건수
   */
  int totalCount();

  int totalCount(BbsFilterCondition filterCondition);

}
