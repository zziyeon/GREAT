package com.kh.great.domain.dao.deal;

import java.util.List;
import java.util.Optional;

public interface DealDAO {

    //구매등록
    Deal add(Deal deal);

    //구매조회 회원번호로 조회
    List<Deal> findByMemberNumber(Long memNumber);

    //주문 번호로 조회
    Optional<Deal> findByOrderNumber(Long orderNumber);

    //구매시 판매갯수 감소
    int update(Long pNumber,Deal deal);
    //구매 취소
    int deleteByOrderNumber(Long orderNumber);

    //구매 추소시 판매갯수 증가
    int delUpdate(Long pNumber,Deal deal);

    //구매 목록

    //남은수량 0개 일시
    int updatePstatus(Long pNumber);

}
