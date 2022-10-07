package com.kh.great.web.controller.notice;

import com.kh.great.domain.common.paging.FindCriteria;
import com.kh.great.domain.dao.notice.BbsFilterCondition;
import com.kh.great.domain.dao.notice.Notice;
import com.kh.great.domain.svc.notice.NoticeSVC;
import com.kh.great.web.dto.notice.DetailForm;
import com.kh.great.web.dto.notice.EditForm;
import com.kh.great.web.dto.notice.ListForm;
import com.kh.great.web.dto.notice.WriteForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {

  private final NoticeSVC noticeSVC;

  @Autowired
  @Qualifier("fc10")
  private FindCriteria fc;

  //글쓰기화면
  @GetMapping("/write")
  public String write(Model model) {
    model.addAttribute("writeForm", new WriteForm());

    return "notice/noticeWriteForm";
  }

  //글쓰기 처리
  @PostMapping("/write")
  public String write(@Valid @ModelAttribute WriteForm writeForm,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes
                      ) {

    //검증 : 제목, 내용 글자수 제한
    if (bindingResult.hasErrors()) {
      log.info("bindingResult : {}", bindingResult);
      return "notice/noticeWriteForm";
    }

    Notice notice = new Notice();
    BeanUtils.copyProperties(writeForm, notice);
    log.info("notice : {}", notice);
    Notice write = noticeSVC.write(notice);

    //otice byId = noticeSVC.read(write.getNoticeId());
    Long noticeId = write.getNoticeId();
    redirectAttributes.addAttribute("id",noticeId);

    return "redirect:/notice/{id}";
  }

  //조회화면
  @GetMapping("/{id}")
  public String read(@PathVariable("id") Long noticeId, DetailForm detailForm, Model model) {
    Notice readNotice = noticeSVC.read(noticeId);

    BeanUtils.copyProperties(readNotice, detailForm);

    model.addAttribute("detailForm", detailForm);
    log.info("실패");

    return "notice/noticeViewForm";
  }

  //수정화면
  @GetMapping("/edit/{id}")
  public String edit(@PathVariable("id") Long noticeId, Model model) {

    Notice modifyNotice = noticeSVC.read(noticeId);

    EditForm editForm = new EditForm();
    BeanUtils.copyProperties(modifyNotice, editForm);

    model.addAttribute("editForm", editForm);

    return "notice/noticeModifyForm";
  }

  //수정처리
  @PostMapping("/edit/{id}")
  public String edit(@PathVariable("id") Long noticeId,
                     @Valid @ModelAttribute("editForm") EditForm editForm,
                     BindingResult bindingResult,
                     RedirectAttributes redirectAttributes) {

    if (bindingResult.hasErrors()) {
      log.info("bindingResult={}", bindingResult);
      return "notice/noticeModifyForm";
    }
    Notice notice = new Notice();
    BeanUtils.copyProperties(editForm, notice);
    noticeSVC.update(noticeId, notice);

    redirectAttributes.addAttribute("id", noticeId);
    return "redirect:/notice/{id}";
  }

  //삭제
  @GetMapping("/{id}/del")
  public String delete(@PathVariable("id") Long noticeId) {

    noticeSVC.delete(noticeId);

    return "redirect:/notice/list";
  }

  //검증 오류 메시지
  private Map<String, String> getErrMsg(BindingResult bindingResult) {
    Map<String, String> errmsg = new HashMap<>();

    bindingResult.getAllErrors().stream().forEach(objectError -> {
      errmsg.put(objectError.getCodes()[0], objectError.getDefaultMessage());
    });

    return errmsg;
  }

  //공지사항 목록화면
  @GetMapping({"/list",
      "/list/{reqPage}",
      "/list/{reqPage}//",
      "/list/{reqPage}/{searchType}/{keyword}"})
  public String listAndReqPage(
      @PathVariable(required = false) Optional<Integer> reqPage,
      @PathVariable(required = false) Optional<String> searchType,
      @PathVariable(required = false) Optional<String> keyword,
      Model model) {
    log.info("/list 요청됨{},{},{},{}",reqPage,searchType,keyword);

    //FindCriteria 값 설정
    fc.getRc().setReqPage(reqPage.orElse(1)); //요청페이지, 요청없으면 1
    fc.setSearchType(searchType.orElse(""));  //검색유형
    fc.setKeyword(keyword.orElse(""));        //검색어

    List<Notice> list = null;
    //검색어 있음
    if(searchType.isPresent() && keyword.isPresent()){
      log.info("1");
      BbsFilterCondition filterCondition = new BbsFilterCondition(
          fc.getRc().getStartRec(), fc.getRc().getEndRec(),
          searchType.get(),
          keyword.get());
      fc.setTotalRec(noticeSVC.totalCount(filterCondition));
      fc.setSearchType(searchType.get());
      fc.setKeyword(keyword.get());
      list = noticeSVC.findAll(filterCondition);

      //검색어 없음
    }else {
      log.info("2");
      //총레코드수
      fc.setTotalRec(noticeSVC.totalCount());
      list = noticeSVC.findAll(fc.getRc().getStartRec(), fc.getRc().getEndRec());
    }

    List<ListForm> partOfList = new ArrayList<>();
    for (Notice notice : list) {
      ListForm listForm = new ListForm();
      BeanUtils.copyProperties(notice, listForm);
      partOfList.add(listForm);
    }

    model.addAttribute("listForm", partOfList);
    model.addAttribute("fc",fc);

    return "notice/noticeMainForm";
  }
}




























