package com.kh.great.domain.svc.deal;

import com.kh.great.domain.dao.deal.Deal;

import java.util.List;
import java.util.Optional;

public interface DealSVC {

    //구매등록
    Deal add(Deal deal);

    //구매조회
    List<Deal> findByMemberNumber(Long memNumber);

    //주문 번호로 조회
    Optional<Deal> findByOrderNumber(Long orderNumber);

    //구매시 상품갯수 감소
    int update(Long pNumber,Deal deal);

    //상품조회


    //구매 취소
    int deleteByOrderNumber(Long orderNumber);

    //구매 취소시 상품갯수 증가
//    int delUpdate(Long pNumber,Deal deal);

    //남은수량 0개 일시
    int updatePstatus(Long pNumber);
}
