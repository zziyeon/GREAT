package com.kh.great.domain.svc.product;

import com.kh.great.domain.dao.product.Product;
import org.springframework.web.bind.annotation.PathVariable;
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

    //상품변경
    int update(Long pNum, Product product);
    int update(Long pNum, Product product, List<MultipartFile> files);

    //상품목록
    List<Product> findAll();

    //상품삭제
    int deleteByProductNum(Long pNum);

    // 오늘 마감 상품 목록
    List<Product> today_deadline();

    //상품 관리
    List<Product> manage(Long ownerNumber);
    List<Product> pManage(@PathVariable("ownerNumber") Long ownerNumber, @RequestParam ("sell_status") Integer sell_status, @RequestParam ("history_start_date") String history_start_date, @RequestParam ("history_end_date") String history_end_date);
    int pManage_status_update(Long pNum, Integer pStatus);

    //상품 관리
    List<Product> saleList(Long ownerNumber);
    List<Product> pSaleList(@PathVariable("ownerNumber") Long ownerNumber, @RequestParam ("pickUp_status") Integer pickUp_status, @RequestParam ("history_start_date") String history_start_date, @RequestParam ("history_end_date") String history_end_date);
    int pickUP_status_update(Long pNum, Integer pickStatus);

    //----------------------------------------------
    // 상품 최신순 목록
//    List <Product> recentList(@RequestParam("zone") String zone);
    List <Product> recentList(@RequestParam Map<String, Object> allParameters);

    // 상품 높은 할인순 목록
    List <Product> discountListDesc(@RequestParam Map<String, Object> allParameters);

    // 상품 높은 가격순 목록
    List <Product> priceList(@RequestParam Map<String, Object> allParameters);

    // 상품 높은 가격순 목록
    List <Product> priceListDesc(@RequestParam Map<String, Object> allParameters);

    //--------------------------------------------------------------------------------
    // 검색 목록
    List<Product> search(@RequestParam ("searchKeyword") String searchKeyword);
}
