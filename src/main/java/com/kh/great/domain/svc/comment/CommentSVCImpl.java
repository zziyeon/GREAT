package com.kh.great.domain.svc.comment;

import com.kh.great.domain.common.file.AttachCode;
import com.kh.great.domain.common.file.FileUtils;
import com.kh.great.domain.dao.article.ArticleDAO;
import com.kh.great.domain.dao.comment.Comment;
import com.kh.great.domain.dao.comment.CommentDAO;
import com.kh.great.domain.dao.uploadFile.UploadFile;
import com.kh.great.domain.svc.uploadFile.UploadFileSVC;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentSVCImpl implements CommentSVC {

  private final CommentDAO commentDAO;
  private final ArticleDAO articleDAO;
  private final UploadFileSVC uploadFileSVC;
  private final FileUtils fileUtils;

  /**
   * 댓글 조회
   *
   * @param commentNum 댓글 번호
   * @return 댓글
   */
  @Override
  public Optional<Comment> find(Long commentNum) {
    return  commentDAO.find(commentNum);
  }

  /**
   * 게시글에 달린 댓글 목록 조회
   *
   * @param articleNum
   * @return 댓글 목록
   */
  @Override
  public List<Comment> findAll(Long articleNum) {

    return commentDAO.findAll(articleNum);
  }

  /**
   * 댓글 작성
   *
   * @param comment 댓글 정보
   * @return 작성된 댓글 수
   */
  @Override
  public Comment save(Comment comment) {

    Long generatedCommentNum = commentDAO.generatedCommentNum();
    comment.setCommentNum(generatedCommentNum);

    if (comment.getCommentGroup() == null) {
      Long generatedCommentGroupNum = commentDAO.generatedCommentGroupNum();
      comment.setCommentGroup(generatedCommentGroupNum);
    }

    commentDAO.save(comment);

    int totalCountOfArticle = commentDAO.totalCountOfArticle(comment.getArticleNum());
    articleDAO.updateCommentsCnt(Long.valueOf(totalCountOfArticle),comment.getArticleNum());

    return commentDAO.find(generatedCommentNum).get();
  }

  /**
   * 댓글 작성 with 첨부파일
   *
   * @param comment 댓글 정보
   * @param file    첨부 파일
   * @return 댓글 정보
   */
  @Override
  public Comment save(Comment comment, MultipartFile file) {
    //1)댓글 등록
    Long generatedCommentNum = commentDAO.generatedCommentNum();
    comment.setCommentNum(generatedCommentNum);

    if (comment.getCommentGroup() == null) {
      Long generatedCommentGroupNum = commentDAO.generatedCommentGroupNum();
      comment.setCommentGroup(generatedCommentGroupNum);
    }

    commentDAO.save(comment);

    int totalCountOfArticle = commentDAO.totalCountOfArticle(comment.getArticleNum());
    articleDAO.updateCommentsCnt(Long.valueOf(totalCountOfArticle),comment.getArticleNum());

    //2)첨부파일
    uploadFileSVC.addFile(file, AttachCode.B0101,generatedCommentNum);


    return commentDAO.find(generatedCommentNum).get();
  }

  /**
   * 댓글 수정
   *
   * @param commentNum 댓글 번호
   * @param comment    댓글 내용
   * @return
   */
  @Override
  public Comment update(Long commentNum, Comment comment) {
    commentDAO.update(commentNum,comment);

    return commentDAO.find(commentNum).get();
  }

  /**
   * 댓글 수정 with 첨부파일
   *
   * @param commentNum   댓글 번호
   * @param comment       댓글 정보
   * @param file 첨부파일
   * @return 댓글 정보
   */
  @Override
  public Comment update(Long commentNum, Comment comment, MultipartFile file) {
    //1)댓글 수정
    commentDAO.update(commentNum,comment);

    //2)첨부파일
    //2-1)기존 첨부파일 메타정보 조회
    List<UploadFile> attachFiles = uploadFileSVC.getFilesByCodeWithRid(AttachCode.B0101.name(), commentNum);
    //2-2)스토리지에서 기존 첨부파일 삭제
    attachFiles.stream().forEach(attachFile-> fileUtils.deleteAttachFile(AttachCode.valueOf(attachFile.getCode()),attachFile.getStoreFilename()));
    //3-2)기본 첨부파일 메타 정보 삭제
    uploadFileSVC.deleteFileByCodeWithRid(AttachCode.B0101.name(),commentNum);

    uploadFileSVC.addFile(file, AttachCode.B0101,commentNum);
    return commentDAO.find(commentNum).get();
  }

  /**
   * 댓글 삭제
   *
   * @param commentNum 댓글 번호
   * @return 댓글 내용
   */
  @Override
  public void delete(Long commentNum) {
    //1)첨부파일 메타정보 조회
    List<UploadFile> attachFiles = uploadFileSVC.getFilesByCodeWithRid(AttachCode.B0101.name(), commentNum);
    //2)스토리지 파일 삭제
    attachFiles.stream().forEach(file-> fileUtils.deleteAttachFile(AttachCode.valueOf(file.getCode()),file.getStoreFilename()));

    //3)댓글 삭제
    Optional<Comment> foundComment = commentDAO.find(commentNum);
    Long articleNum = foundComment.get().getArticleNum();

    commentDAO.delete(commentNum);

    int totalCountOfArticle = commentDAO.totalCountOfArticle(articleNum);
    articleDAO.updateCommentsCnt(Long.valueOf(totalCountOfArticle),articleNum);

    //4)첨부파일 메타 정보 삭제
    uploadFileSVC.deleteFileByCodeWithRid(AttachCode.B0101.name(),commentNum);
  }

  /**
   * 자식 댓글 수 조회
   *
   * @param commentNum 삭제할 댓글 번호
   * @return 자식 댓글 수
   */
  @Override
  public int countOfChildrenComments(Long commentNum) {
    return commentDAO.countOfChildrenComments(commentNum);
  }

  /**
   * 자식 댓글이 있는 댓글 삭제 처리
   *
   * @param commentNum 삭제할 댓글 번호
   * @return
   */
  @Override
  public int updateToDeletedComment(Long commentNum) {
    return commentDAO.updateToDeletedComment(commentNum);
  }


//  /**
//   * 게시물 댓글 건수 조회
//   *
//   * @param articleNum 게시글 번호
//   * @return 댓글 건수
//   */
//  @Override
//  public int totalCountOfArticle(Long articleNum) {
//    return commentDAO.totalCountOfArticle(articleNum);
//  }
}
