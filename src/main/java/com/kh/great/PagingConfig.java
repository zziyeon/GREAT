package com.kh.great;

import com.kh.great.domain.common.paging.PageCriteria;
import com.kh.great.domain.common.paging.RecordCriteria;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PagingConfig {
  // 한 화면에 나타나는 최대 개수
  private static final  int REC_COUNT_5_PER_PAGE = 5;
  private static final  int PAGE_COUNT_8_PER_PAGE = 8;

  @Bean
  public RecordCriteria rc5() {
    return new RecordCriteria(REC_COUNT_5_PER_PAGE);
  }
  @Bean
  public PageCriteria pc8(){
    return new PageCriteria(rc5(), PAGE_COUNT_8_PER_PAGE);
  }

}
