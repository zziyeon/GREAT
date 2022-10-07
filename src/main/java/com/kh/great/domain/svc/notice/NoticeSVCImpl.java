package com.kh.great.domain.svc.notice;

import com.kh.great.domain.dao.notice.BbsFilterCondition;
import com.kh.great.domain.dao.notice.Notice;
import com.kh.great.domain.dao.notice.NoticeDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class NoticeSVCImpl implements NoticeSVC {

  private final NoticeDAO noticeDAO;

  /**
   * 목록
   *
   * @return
   */
  @Override
  public List<Notice> foundAll() {
    return noticeDAO.foundAll();
  }

  @Override
  public List<Notice> findAll(int startRec, int endRec) {
    return noticeDAO.findAll(startRec,endRec);
  }

  /**
   * 검색
   *
   * @param filterCondition 분류,시작레코드번호,종료레코드번호,검색유형,검색어
   * @return
   */
  @Override
  public List<Notice> findAll(BbsFilterCondition filterCondition) {
    return noticeDAO.findAll(filterCondition);
  }

  /**
   * 등록
   * @param notice
   * @return
   */
  @Override
  public Notice write(Notice notice) {

    return noticeDAO.save(notice);
  }

  /**
   * 전체조회
   * @return
   */
  @Override
  public List<Notice> findAll() {
    return noticeDAO.selectAll();
  }

  /**
   * 상세조회
   *
   * @param noticeId
   * @return
   */
  @Override
  public Notice read(Long noticeId) {
    noticeDAO.increaseViewCount(noticeId);
    return noticeDAO.read(noticeId);
  }

  /**
   * 게시글 수정
   *
   * @param noticeId 게시글 번호
   * @param notice    게시글 수정 내용
   */
  @Override
  public Notice update(Long noticeId, Notice notice) {
    noticeDAO.update(noticeId, notice);
    return noticeDAO.read(noticeId);
  }

  @Override
  public Notice update(Long noticeId, Notice notice, List<MultipartFile> files) {
    noticeDAO.update(noticeId, notice);

    return noticeDAO.read(noticeId);
  }

  /**
   * 삭제
   * @param noticeId
   * @return
   */
  @Override
  public void delete(Long noticeId) {

   noticeDAO.delete(noticeId);
  }

  //전체건수
  @Override
  public int totalCount() {
    return noticeDAO.totalCount();
  }

  @Override
  public int totalCount(BbsFilterCondition filterCondition) {
    return noticeDAO.totalCount(filterCondition);
  }
}