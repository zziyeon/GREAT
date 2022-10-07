package com.kh.great.domain.dao.article;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class ArticleFilterCondition {
  private String category;      //분류 코드
  private int startRec;         //시작 레코드 번호
  private int endRec;           //종료 레코드 번호
  private String searchType;    //검색 유형
  private String keyword;       //검색어

  public ArticleFilterCondition(String category, String searchType, String keyword) {
    this.category = category;
    this.searchType = searchType;
    this.keyword = keyword;
  }
}
