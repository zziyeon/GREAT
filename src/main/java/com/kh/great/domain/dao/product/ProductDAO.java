package com.kh.great.domain.dao.product;

import com.kh.great.domain.Product;

import java.util.List;

public interface ProductDAO {
    //상품번호 생성
    Long generatePnum();
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

    //상품관리목록
    List<Product> pManage(Long ownerNumber);
}
