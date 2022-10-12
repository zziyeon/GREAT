package com.kh.great.domain.dao.product;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

public interface ProductDAO {
    /**
     * 상품 등록
     * @param product 상품정보
     * @return 등록한 상품 수
     */
    Long save(Product product);

    /**
     * 상품조회
     * @param pNum 상품번호
     * @return 상품 정보
     */
    Product findByProductNum(Long pNum);

    /**
     * 상품 변경
     * @param pNum 상품번호
     * @param product 상품정보
     * @return 수정된 판매글 수
     */
    int update(Long pNum, Product product);

    /**
     * 상품 목록
     * @return 상품 목록
     */
    List<Product> findAll();

    /**
     * 상품 삭제
     * @param pNum 상품 번호
     * @return 삭제된 상품 수
     */
    int deleteByProductNum(Long pNum);

    /**
     * 오늘 마감 상품 목록
     * @return 오늘 마감 상품 목록
     */
    List<Product> today_deadline();

    /**
     * 상품 관리 목록
     * @param ownerNumber 점주 회원 번호
     * @return 상품 관리 목록
     */
    List<Product> manage(Long ownerNumber);

    /**
     * 상품 관리 목록
     * @param ownerNumber 점주 회원 번호
     * @param sell_status 상품 상태
    * @param history_start_date 날짜 조회 시작일
     * @param history_end_date 날짜 조회 종료일
     * @return 상품 목록
     */
    List<Product> pManage(@PathVariable("ownerNumber") Long ownerNumber,  @RequestParam ("sell_status") Integer sell_status,
                          @RequestParam ("history_start_date") String history_start_date, @RequestParam ("history_end_date") String history_end_date);

    /**
     * 판매 상태 변경
     * @param pNum 변경할 상품번호
     * @param pStatus 판매 상태
     * @return 변경할 상품 수
     */
    int pManage_status_update(Long pNum, Integer pStatus);

    //판매 내역
    List<Product> saleList(Long ownerNumber);
    List<Product> pSaleList(@PathVariable("ownerNumber") Long ownerNumber,  @RequestParam ("pickUp_status") Integer pickUp_status, @RequestParam ("history_start_date") String history_start_date, @RequestParam ("history_end_date") String history_end_date);
    int pickUP_status_update(Long pNum, Integer pickStatus);

    //------------------------------------
    // 상품 최신순 목록
    List <Product> recentList(@RequestParam Map<String, Object> allParameters);

    // 상품 높은 할인순 목록
    List <Product> discountListDesc(@RequestParam Map<String, Object> allParameters);

    // 상품 높은 가격순 목록
    List <Product> priceList(@RequestParam Map<String, Object> allParameters);

    /**
     * 높은 가격순 목록
     * @param allParameters 모든 파라미터(ApiProductController에서 정한_지역 파라미터, 카테고리 파라미터)
     * @return 상품 목록
     */
    List <Product> priceListDesc(@RequestParam Map<String, Object> allParameters);

    //----------------------------------------------------------
    /**
     * 키워드 검색 목록
     * @param searchKeyword 검색 키워드
     * @return 검색된 상품 목록
     */
    List<Product> search(@RequestParam ("searchKeyword") String searchKeyword);
}