package com.kh.great.web.controller.community;


import com.kh.great.domain.common.paging.FindCriteria;
import com.kh.great.domain.dao.article.Article;
import com.kh.great.domain.dao.article.ArticleFilterCondition;
import com.kh.great.domain.svc.article.ArticleSVC;
import com.kh.great.web.api.ApiResponse;
import com.kh.great.web.dto.article.ArticleAddForm;
import com.kh.great.web.dto.article.ArticleEditForm;
import com.kh.great.web.dto.article.ArticleForm;
import com.kh.great.web.dto.article.BoardForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@Controller
@RequestMapping("/community")
@RequiredArgsConstructor
public class ArticleController {

  private final ArticleSVC articleSVC;

  @Autowired
  @Qualifier("fc10") //동일한 타입의 객체가 여러개있을때 빈이름을 명시적으로 지정해서 주입받을때
  private FindCriteria fc;

  //자유게시판 화면 : 전체
  @GetMapping({"/list",
      "/list/{reqPage}",
      "/list/{reqPage}//", //?
      "/list/{reqPage}/{searchType}/{keyword}"})
  public String board(
      @PathVariable(required = false) Optional<Integer> reqPage,
      @PathVariable(required = false) Optional<String> searchType,
      @PathVariable(required = false) Optional<String> keyword,
      @RequestParam(required = false) Optional<String> category, //왜 카테고리만 @RequestParam?
      Model model) {
    log.info("/list 요청됨{},{},{},{}", reqPage, searchType, keyword, category);

    String cate = getCategory(category);

    //FindCriteria 값 설정
    fc.getRc().setReqPage(reqPage.orElse(1)); //요청페이지, 요청없으면 1
    fc.setSearchType(searchType.orElse(""));  //검색유형
    fc.setKeyword(keyword.orElse(""));        //검색어

    List<Article> list = null;
    //게시물 목록 전체
    if (category == null || StringUtils.isEmpty(cate)) {

      //검색어 있음
      if (searchType.isPresent() && keyword.isPresent()) {
        ArticleFilterCondition filterCondition = new ArticleFilterCondition(
            "", fc.getRc().getStartRec(), fc.getRc().getEndRec(),
            searchType.get(),
            keyword.get());
        fc.setTotalRec(articleSVC.totalCount(filterCondition));
        fc.setSearchType(searchType.get());
        fc.setKeyword(keyword.get());
        list = articleSVC.findAll(filterCondition);

        //검색어 없음
      } else {
        //총레코드수
        fc.setTotalRec(articleSVC.totalCount());
        list = articleSVC.findAll(fc.getRc().getStartRec(), fc.getRc().getEndRec());
      }

      //카테고리별 목록
    } else {
      //검색어 있음
      if (searchType.isPresent() && keyword.isPresent()) {
        ArticleFilterCondition filterCondition = new ArticleFilterCondition(
            category.get(), fc.getRc().getStartRec(), fc.getRc().getEndRec(),
            searchType.get(),
            keyword.get());
        fc.setTotalRec(articleSVC.totalCount(filterCondition));
        fc.setSearchType(searchType.get());
        fc.setKeyword(keyword.get());
        list = articleSVC.findAll(filterCondition);
        //검색어 없음
      } else {
        fc.setTotalRec(articleSVC.totalCount(cate));
        list = articleSVC.findAll(cate, fc.getRc().getStartRec(), fc.getRc().getEndRec());
      }
    }

    List<BoardForm> partOfList = new ArrayList<>();
    for (Article article : list) {
      BoardForm boardForm = new BoardForm();
      BeanUtils.copyProperties(article, boardForm);
      partOfList.add(boardForm);
    }

    model.addAttribute("list", partOfList);
    model.addAttribute("fc", fc);
    model.addAttribute("category", cate);

    return "/community/board";
  }

  //글쓰기 화면
  @GetMapping("/write")
  public String writePage(Model model) {
    model.addAttribute("articleAddForm", new ArticleAddForm());
    return "/community/writeForm";
  }

  //글쓰기 처리
  @ResponseBody
  @PostMapping("/write")
  public ApiResponse<Object> write(@Valid @RequestBody ArticleAddForm articleAddForm,
                                   BindingResult bindingResult) {

    log.info("articleAddForm : {}", articleAddForm);

    //검증 : 제목, 내용 글자수 제한
    if (bindingResult.hasErrors()) {
      log.info("bindingResult : {}", bindingResult);
      return ApiResponse.createApiResMsg("99", "실패", getErrMsg(bindingResult));
    }

    Article article = new Article();
    BeanUtils.copyProperties(articleAddForm, article);
    log.info("article : {}", article);
    Article savedArticle = articleSVC.save(article);

    return ApiResponse.createApiResMsg("00", "성공", savedArticle);
  }

  //글수정 화면
  @GetMapping("edit/{id}")
  public String editPage(@PathVariable("id") Long articleNum,
                         Model model) {

    Optional<Article> foundArticle = articleSVC.read(articleNum);
    ArticleEditForm articleEditForm = new ArticleEditForm();
    if (!foundArticle.isEmpty()) {
      BeanUtils.copyProperties(foundArticle.get(), articleEditForm);
    }

    model.addAttribute("articleEditForm", articleEditForm);

    return "/community/editForm";
  }

  //글수정 처리
  @ResponseBody
  @PatchMapping("edit/{id}")
  public ApiResponse<Object> edit(@PathVariable("id") Long articleNum,
                                  @Valid @RequestBody ArticleEditForm articleEditForm,
                                  BindingResult bindingResult) {

    log.info("articleEditForm : {}", articleEditForm);
    //검증 : 제목, 내용 글자수 제한
    if (bindingResult.hasErrors()) {
      log.info("bindingResult : ", bindingResult);
      return ApiResponse.createApiResMsg("99", "실패", getErrMsg(bindingResult));
    }

    Article article = new Article();
    BeanUtils.copyProperties(articleEditForm, article);
    log.info("article : {}", article);
    Article updatedArticle = articleSVC.update(articleNum, article);

    return ApiResponse.createApiResMsg("00", "성공", updatedArticle);
  }

  //게시글 조회
  @GetMapping("/article/{id}")
  public String read(@PathVariable("id") Long articleNum,
                     Model model) {

    Optional<Article> foundArticle = articleSVC.read(articleNum);
    ArticleForm articleForm = new ArticleForm();
    if (!foundArticle.isEmpty()) {
      BeanUtils.copyProperties(foundArticle.get(), articleForm);
    }
    log.info("아티클폼 : {}",articleForm);
    model.addAttribute("articleForm", articleForm);

    return "/community/article";
  }

  //게시글 삭제
  @ResponseBody
  @DeleteMapping("/article/{id}/del")
  public ApiResponse<Article> delete(@PathVariable("id") Long articleNum) {
    log.info("게시글 번호 : {}", articleNum);
    articleSVC.delete(articleNum);

    return ApiResponse.createApiResMsg("00", "성공", null);
  }

  //검증 오류 메시지
  private Map<String, String> getErrMsg(BindingResult bindingResult) {
    Map<String, String> errmsg = new HashMap<>();

    bindingResult.getAllErrors().stream().forEach(objectError -> {
      errmsg.put(objectError.getCodes()[0], objectError.getDefaultMessage());
    });

    return errmsg;
  }

  //쿼리스트링 카테고리 읽기, 없으면 ""반환
  private String getCategory(Optional<String> category) {
    String cate = category.isPresent() ? category.get() : "";
    log.info("category={}", cate);
    return cate;
  }
}
