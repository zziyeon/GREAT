package com.kh.great.domain.svc.article;


import com.kh.great.domain.common.file.AttachCode;
import com.kh.great.domain.common.file.FileUtils;
import com.kh.great.domain.dao.article.Article;
import com.kh.great.domain.dao.article.ArticleDAO;
import com.kh.great.domain.dao.article.ArticleFilterCondition;
import com.kh.great.domain.dao.uploadFile.UploadFile;
import com.kh.great.domain.svc.uploadFile.UploadFileSVC;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleSVCImpl implements ArticleSVC {

  private final ArticleDAO articleDAO;
  private final UploadFileSVC uploadFileSVC;
  private final FileUtils fileUtils;

  /**
   * 게시글 목록 조회1 : 전체
   *
   * @return 게시글 리스트
   */
  @Override
  public List<Article> findAll() {
    return articleDAO.findAll();
  }

  /**
   * 게시글 목록 조회2 : 카테고리
   *
   * @param category 카테고리
   * @return 게시글 리스트
   */
  @Override
  public List<Article> findAll(String category) {
    return articleDAO.findAll(category);
  }

  /**
   * 게시글 목록 조회3 : 페이지
   *
   * @param startRec 첫 페이지
   * @param endRec   마지막 페이지
   * @return 게시글 리스트
   */
  @Override
  public List<Article> findAll(int startRec, int endRec) {
    return articleDAO.findAll(startRec, endRec);
  }

  /**
   * 게시글 목록 조회4 : 카테고리, 페이지
   *
   * @param category 카테고리
   * @param startRec 첫 페이지
   * @param endRec   마지막 페이지
   * @return 게시글 리스트
   */
  @Override
  public List<Article> findAll(String category, int startRec, int endRec) {
    return articleDAO.findAll(category, startRec, endRec);
  }

  /**
   * 게시글 목록 조회5 : 검색
   *
   * @param filterCondition 분류, 시작 레코드 번호, 종료 레코드 번호, 검색 유형, 검색어
   * @return
   */
  @Override
  public List<Article> findAll(ArticleFilterCondition filterCondition) {
    return articleDAO.findAll(filterCondition);
  }

  /**
   * 게시글 조회
   *
   * @param articleNum 게시글 번호
   * @return 게시글
   */
  @Override
  public Optional<Article> read(Long articleNum) {
    articleDAO.increaseViewCount(articleNum);
    return articleDAO.read(articleNum);
  }

  /**
   * 게시글 작성
   *
   * @param article 게시글 작성 내용
   * @return 게시글
   */
  @Override
  public Article save(Article article) {

    Long generatedArticleNum = articleDAO.generatedArticleNum();
    article.setArticleNum(generatedArticleNum);
    articleDAO.save(article);

    return articleDAO.read(generatedArticleNum).get();
  }


  @Override
  public Article save(Article article, List<MultipartFile> files) {
    Long generatedArticleNum = articleDAO.generatedArticleNum();
    article.setArticleNum(generatedArticleNum);
    articleDAO.save(article);

    //첨부파일-이미지
    uploadFileSVC.addFile(files, AttachCode.B0101, generatedArticleNum);

    return articleDAO.read(generatedArticleNum).get();
  }

  /**
   * 게시글 수정
   *
   * @param articleNum 게시글 번호
   * @param article    게시글 수정 내용
   */
  @Override
  public Article update(Long articleNum, Article article) {
    articleDAO.update(articleNum, article);
    return articleDAO.read(articleNum).get();
  }

  @Override
  public Article update(Long articleNum, Article article, List<MultipartFile> files) {
    articleDAO.update(articleNum, article);

    //2)첨부파일-이미지
    uploadFileSVC.addFile(files, AttachCode.B0101, articleNum);

    return articleDAO.read(articleNum).get();
  }

  /**
   * 게시글 삭제
   *
   * @param articleNum
   */
  @Override
  public void delete(Long articleNum) {
    //1)첨부파일 메타정보 조회
    List<UploadFile> attachFiles = uploadFileSVC.getFilesByCodeWithRid(AttachCode.B0101.name(), articleNum);
    //List<UploadFile> imageFiles = uploadFileSVC.getFilesByCodeWithRid(AttachCode.P0102.name(), articleNum);

    //2)스토리지 파일 삭제
    List<UploadFile> unionFiles = new LinkedList<>();
    unionFiles.addAll(attachFiles);
    //unionFiles.addAll((imageFiles));
    for (UploadFile file : unionFiles) {
      fileUtils.deleteAttachFile(AttachCode.valueOf(file.getCode()), file.getStoreFilename());
    }

    //3)게시글 삭제
    articleDAO.delete(articleNum);

    //메타정보 삭제
    uploadFileSVC.deleteFileByCodeWithRid(AttachCode.B0101.name(), articleNum);
    //uploadFileSVC.deleteFileByCodeWithRid(AttachCode.P0102.name(), articleNum);
  }

  /**
   * 전체 건수
   *
   * @return 게시글 전체 건수
   */
  @Override
  public int totalCount() {
    return articleDAO.totalCount();
  }

  /**
   * 전체 건수
   *
   * @param category 게시글 유형
   * @return 게시글 전체 건수
   */
  @Override
  public int totalCount(String category) {
    return articleDAO.totalCount(category);
  }

  /**
   * 전체 건수
   *
   * @param filterCondition 필터 조건
   * @return 게시글 전체 건수
   */
  @Override
  public int totalCount(ArticleFilterCondition filterCondition) {
    return articleDAO.totalCount(filterCondition);
  }

}
