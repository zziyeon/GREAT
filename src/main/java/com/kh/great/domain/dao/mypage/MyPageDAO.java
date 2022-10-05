package com.kh.great.domain.dao.mypage;


import com.kh.great.domain.dao.member.Member;

import java.util.List;
import java.util.Optional;

public interface MyPageDAO {

    //리뷰등록
    Review save(Review review);

    //리뷰조회 - 회원번호
    List<Review> findByMemNumber(Long memNumber);

    //리뷰조회 - 프로필에서 조회
    List<Review> findByBuyerNumber(Long memNumber);

    //리뷰조회 - 리뷰번호
    Optional<Review> findByReviewNumber(Long reviewNumber);

    //리뷰변경
    int update(Long reviewNumber,Review review);

    //리뷰삭제
    int deleteByReviewId(Long reviewNumber);

    //회원 조회 - 회원 번호
    Optional<Member> findMember(Long memNumber);

    //즐겨찾기 추가
    Bookmark addBookmark(Bookmark bookmark);

    //즐겨찾기 조회
    Optional<Bookmark> findBookmarkNumber(Long bookmarkNumber);

    //즐겨찾기 삭제 -프로필에서
    int delBookmark(Long memNUmber);

    //즐겨찾기 삭제 - 내 즐겨찾기에서
    int delBookmarkInMyPage(Long bookmarkNumber);

    //즐겨찾기 회원 조회
    List<Bookmark> findBookmark(Long memNumber);
}