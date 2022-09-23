package com.kh.great.domain.svc.product;

import com.kh.great.domain.Product;

import java.util.List;

public interface ProductSVC {

    //상품 등록
    Product save(Product product);

    //상품 조회
    Product findByProductNum(Long pNum);

    //상품 변경
    int update(Long pNum, Product product);

    //상품 목록
    List<Product> findAll();

    //상품 삭제
    int deleteByProductNum(Long pNum);

    //상품 관리
    List<Product> pManage(Long ownerNumber);
}
