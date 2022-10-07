package com.kh.great.web.controller.community;


import com.kh.great.domain.common.file.AttachCode;
import com.kh.great.domain.dao.comment.Comment;
import com.kh.great.domain.dao.uploadFile.UploadFile;
import com.kh.great.domain.svc.comment.CommentSVC;
import com.kh.great.domain.svc.uploadFile.UploadFileSVC;
import com.kh.great.web.api.ApiResponse;
import com.kh.great.web.dto.comment.CommentAddForm;
import com.kh.great.web.dto.comment.CommentEditForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

  private final CommentSVC commentSVC;
  private final UploadFileSVC uploadFileSVC;

  //댓글 목록 조회
  @GetMapping("/list/{articleNum}")
  public ApiResponse<List<Comment>> commentList(@PathVariable("articleNum") Long articleNum) {

    List<Comment> commentList = commentSVC.findAll(articleNum);

    for (Comment comment : commentList) {
      List<UploadFile> uploadFile = uploadFileSVC.getFilesByCodeWithRid(AttachCode.B0101.name(), comment.getCommentNum());
      if (uploadFile.size() > 0) {
        UploadFile attachFile = uploadFile.get(0);
        comment.setAttachFile(attachFile);
      }
    }
    log.info("commentList : {}",commentList);
    return ApiResponse.createApiResMsg("00", "성공", commentList);
  }

  //댓글 등록
  @PostMapping("/write/{articleNum}")
  public ApiResponse<Comment> saveComment(@PathVariable Long articleNum,
                                          CommentAddForm commentAddForm) {

    log.info("commentAddForm : {}",commentAddForm);
    Comment comment = new Comment();
    BeanUtils.copyProperties(commentAddForm, comment);
    comment.setArticleNum(articleNum); //꼭 필요할까?

    //댓글 등록
    Comment savedComment = new Comment();

    //주의 : view에서 multiple인 경우 파일 첨부가 없더라도 빈문자열("")이 반환되어
    // List<MultipartFile>에 빈 객체 1개가 포함됨
    if (commentAddForm.getFile() == null) {
      savedComment = commentSVC.save(comment);
      log.info("댓글컨트롤러1");
    } else if (commentAddForm.getFile() != null) {
      savedComment = commentSVC.save(comment, commentAddForm.getFile());
      log.info("댓글컨트롤러2");
    }
    log.info("댓글컨트롤러3");
    log.info("savedComment : {}",savedComment);
    return ApiResponse.createApiResMsg("00", "성공", savedComment);
  }

  //댓글 수정 창
  @GetMapping("/edit/{commentNum}")
  public ApiResponse<Comment> editWindow(@PathVariable Long commentNum) {
    //수정할 댓글 조회
    Optional<Comment> foundComment = commentSVC.find(commentNum);

    //첨부파일 조회
    List<UploadFile> uploadFile = uploadFileSVC.getFilesByCodeWithRid(AttachCode.B0101.name(), commentNum);
    if (uploadFile.size() > 0) {
      UploadFile attachFile = uploadFile.get(0);
      foundComment.get().setAttachFile(attachFile);
    }

    return ApiResponse.createApiResMsg("00", "성공", foundComment.get());
  }

  //댓글 수정 처리
  @PatchMapping("/edit/{commentNum}")
  public ApiResponse<Comment> editComment(@PathVariable Long commentNum,
                                          CommentEditForm commentEditForm) {

    Comment comment = new Comment();
    BeanUtils.copyProperties(commentEditForm, comment);

    //댓글 수정
//    Comment updatedComment = commentSVC.update(commentNum, comment);
    Comment updatedComment = new Comment();


    if (commentEditForm.getFile() == null) {
      updatedComment = commentSVC.update(commentNum, comment);
    } else if (commentEditForm.getFile() != null) {
      updatedComment = commentSVC.update(commentNum, comment, commentEditForm.getFile());
    }

    return ApiResponse.createApiResMsg("00", "성공", updatedComment);
  }

  //댓글 삭제
  @DeleteMapping("/delete/{commentNum}")
  public ApiResponse<Comment> deleteComment(@PathVariable Long commentNum) {

    commentSVC.delete(commentNum);

    return ApiResponse.createApiResMsg("00", "성공", null);
  }

}
