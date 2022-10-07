package com.kh.great.domain.svc.mypage;


import com.kh.great.domain.dao.member.Member;
import com.kh.great.domain.dao.mypage.Bookmark;
import com.kh.great.domain.dao.mypage.Good;
import com.kh.great.domain.dao.mypage.Review;
import com.kh.great.domain.dao.product.Product;

import java.util.List;
import java.util.Optional;

public interface MyPageSVC {
//    void update(Long memNumber, Member member, List<MultipartFile> files);
    Review save(Review review);

    //리뷰조회 - 회원번호로 조회
    List<Review> findByMemNumber(Long memNumber);

    //리뷰조회 - 리뷰번호로 조회
    Optional<Review> findByReviewNumber(Long reviewNumber);

    //리뷰조회 - 프로필에서 조회
    List<Review> findBySellerNumber(Long memNumber);

    //판매글조회 - 프로필에서
    List<Product> findByOwnerNumber(Long ownerNumber);

    //리뷰변경
    int update(Long reviewNumber,Review review);

    //리뷰삭제
    int deleteByReviewId(Long reviewNumber);

    //회원 조회
    Optional<Member> findMember(Long memNumber);

    //즐겨찾기 추가
    Bookmark addBookmark(Bookmark bookmark);

    //즐겨찾기 회원 조회
    List<Bookmark> findBookmark(Long memNumber);

    //즐겨찾기 조회
    Optional<Bookmark> findBookmarkNumber(Long bookmarkNumber);

    //즐겨찾기 삭제 - 프로필에서 삭제
    int delBookmark(Long memNumber);

    //즐겨찾기 삭제 - 내 즐겨찾기에서 삭제
    int delBookmarkInMyPage(Long bookmarkNumber);

    //좋아요 추가
    Good addGood(Good good);

    //좋아요 삭제 - 판매글에서 삭제
    int delGood(Long pNumber);

    //좋아요 삭제 - 내 좋아요 화면에서 삭제
    int delGoodInMyPage(Long goodNumber);

    //좋아요 회원 조회
    List<Good> findGoods(Long memNumber);
}