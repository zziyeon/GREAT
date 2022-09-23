package com.kh.great.domain.svc.product;

import com.kh.great.domain.Product;

import java.util.List;

public interface ProductSVC {

    //상품등록
    Product save(Product product);

    //상품조회
    Product findByProductNum(Long pNum);

    //상품변경
    int update(Long pNum, Product product);

    //상품목록
    List<Product> findAll();

    //상품삭제
    int deleteByProductNum(Long pNum);

    //상품 관리
    List<Product> pManage(Long ownerNumber);
}
