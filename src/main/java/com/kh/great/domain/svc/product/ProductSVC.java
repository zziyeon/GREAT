package com.kh.great.domain.svc.product;

import com.kh.great.domain.dao.product.Product;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ProductSVC {

    //상품등록
    Long save(Product product);

    Long save (Product product, List<MultipartFile> files);

    //상품조회
    Product findByProductNum(Long pNum);

    // 상품 검색
    List<Product> select(String findStr);

    //상품변경
    int update(Long pNum, Product product);

    //상품목록
    List<Product> findAll();

    //상품삭제
    int deleteByProductNum(Long pNum);

    // 오늘 마감 상품 목록
    List<Product> today_deadline();

    //상품 관리
    List<Product> pManage(Long ownerNumber);

    //상품 관리
    List<Product> saleList(Long ownerNumber);

    //----------------------------------------------
    // 상품 최신순 목록
//    List <Product> recentList(@RequestParam("zone") String zone);
    List <Product> recentList(@RequestParam Map<String, Object> allParameters);

    // 상품 높은 할인순 목록
    List <Product> discountListDesc(@RequestParam("zone") String zone);

    // 상품 높은 가격순 목록
    List <Product> priceList(@RequestParam("zone") String zone);

    // 상품 높은 가격순 목록
    List <Product> priceListDesc(@RequestParam("zone") String zone);


    //--------------------------------------------------
    //한식
    List <Product> kFood();
}
