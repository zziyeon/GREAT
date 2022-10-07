package com.kh.great.domain.svc.mypage;

import com.kh.great.domain.common.file.AttachCode;
import com.kh.great.domain.dao.member.Member;
import com.kh.great.domain.dao.member.MemberDAO;
import com.kh.great.domain.dao.mypage.Bookmark;
import com.kh.great.domain.dao.mypage.Good;
import com.kh.great.domain.dao.mypage.MyPageDAO;
import com.kh.great.domain.dao.mypage.Review;
import com.kh.great.domain.dao.product.Product;
import com.kh.great.domain.svc.uploadFile.UploadFileSVC;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyPageSVCImpl implements MyPageSVC {
    private final MyPageDAO myPageDAO;
    private final MemberDAO memberDAO;

//    @Override
//    public void update(Long memNumber, Member member, List<MultipartFile> files){
//        //2) 첨부파일- 상품 설명
//        uploadFileSVC.addFile(files, AttachCode.M0101, memNumber);
//    }

//    public Long update(Member member, List<MultipartFile> files) {
//        Long num = memberDAO.findMemNumber(member.getMemEmail());
//        uploadFileSVC.addFile(files, AttachCode.M0101, num);
//        return num;
//    }

    //리뷰 등록
    @Override
    public Review save(Review review) {
        return myPageDAO.save(review);
    }

    //리뷰조회 - 회원번호로 조회
    @Override
    public List<Review> findByMemNumber(Long memNumber) {
        return myPageDAO.findByMemNumber(memNumber);
    }

    //리뷰조회 - 리뷰번호로 조회
    @Override
    public Optional<Review> findByReviewNumber(Long reviewNumber) {
        return myPageDAO.findByReviewNumber(reviewNumber);
    }

    //리뷰조회 - 프로필에서 조회
    @Override
    public List<Review> findBySellerNumber(Long memNumber) {
        return myPageDAO.findBySellerNumber(memNumber);
    }

    //판매글조회 - 프로필에서
    @Override
    public List<Product> findByOwnerNumber(Long ownerNumber) {
        return myPageDAO.findByOwnerNumber(ownerNumber);
    }

    //리뷰 수정
    @Override
    public int update(Long reviewNumber, Review review) {
        return myPageDAO.update(reviewNumber,review);
    }

    //리뷰 삭제
    @Override
    public int deleteByReviewId(Long reviewNumber) {
        return myPageDAO.deleteByReviewId(reviewNumber);
    }

    //회원 조회
    @Override
    public Optional<Member> findMember(Long memNumber) {
        return myPageDAO.findMember(memNumber);
    }

    //즐겨찾기 추가
    @Override
    public Bookmark addBookmark(Bookmark bookmark) {
        return myPageDAO.addBookmark(bookmark);
    }

    //즐겨찾기 회원 조회
    @Override
    public List<Bookmark> findBookmark(Long memNumber) {
        return myPageDAO.findBookmark(memNumber);
    }

    //즐겨찾기 번호 조회
    @Override
    public Optional<Bookmark> findBookmarkNumber(Long bookmarkNumber) {
        return myPageDAO.findBookmarkNumber(bookmarkNumber);
    }

    //즐겨찾기 삭제 - 프로필에서 삭제
    @Override
    public int delBookmark(Long memNumber) {
        return myPageDAO.delBookmark(memNumber);
    }

    //즐겨찾기 삭제 - 내 즐겨찾기에서 삭제
    @Override
    public int delBookmarkInMyPage(Long bookmarkNumber) {
        return myPageDAO.delBookmarkInMyPage(bookmarkNumber);
    }

    //좋아요 추가
    @Override
    public Good addGood(Good good) {
        return myPageDAO.addGood(good);
    }

    //좋아요 삭제 -판매 화면에서 삭제
    @Override
    public int delGood(Long pNumber) {
        return myPageDAO.delGood(pNumber);
    }

    //좋아요 삭제 - 내 좋아요 화면에서 삭제
    @Override
    public int delGoodInMyPage(Long goodNumber) {
        return myPageDAO.delGoodInMyPage(goodNumber);
    }

    //좋아요 회원 조회
    @Override
    public List<Good> findGoods(Long memNumber) {
        return myPageDAO.findGoods(memNumber);
    }
}