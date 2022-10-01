package com.kh.great.domain.dao.product;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
class ProductDAOImplTest {
  @Autowired
  private ProductDAO productDAO;

  @Test
  @DisplayName("카테고리별 목록")
  void kFood( ) {
    List<Product> productList = productDAO.kFood();

    log.info("상품수={}", productList.size());



//    productList.stream().forEach(product1 -> log.info(product1.toString()));


  }
}