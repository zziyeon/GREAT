package com.kh.great.web.controller.mypage;

import com.kh.great.domain.dao.deal.Deal;
import com.kh.great.domain.dao.member.Member;
import com.kh.great.domain.dao.mypage.Bookmark;
import com.kh.great.domain.dao.mypage.Review;
import com.kh.great.domain.svc.deal.DealSVC;
import com.kh.great.domain.svc.mypage.MyPageSVC;
import com.kh.great.domain.svc.product.ProductSVC;
import com.kh.great.web.api.ApiResponse;
import com.kh.great.web.dto.mypage.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {

    private final DealSVC dealSVC;
    private final MyPageSVC myPageSVC;
    private final ProductSVC productSVC;

    //주문 내역
    @GetMapping("/{id}")
    public String myPage(@PathVariable("id") Long memNumber, Model model){

        Optional<Member> member = myPageSVC.findMember(memNumber);
        Member member1 = member.get();
        MemberForm memberForm = new MemberForm();
        BeanUtils.copyProperties(member1,memberForm);


        List<Deal> deals = dealSVC.findByMemberNumber(memNumber);

        List<Deal> list = new ArrayList<>();
        deals.stream().forEach(deal ->{
            BeanUtils.copyProperties(deal, new ReviewInfoForm());
            list.add(deal);
        });
        log.info("list={}",list);
        log.info("memberForm={}",memberForm);

        model.addAttribute("list",list);
        model.addAttribute("form",memberForm);

        return "mypage/order-history";
    }

    //리뷰 등록 양식
    @GetMapping("/review/add")
    public String saveForm(Model model){

        ReviewAddForm reviewAddForm = new ReviewAddForm();

        reviewAddForm.setBuyerNumber(1l);
        log.info("reviewAddForm={}",reviewAddForm);
        model.addAttribute("form",reviewAddForm);
        return "mypage/reviewAdd";
    }

    //리뷰 등록 처리
    @PostMapping("/review/add")
    public String save(@ModelAttribute("form") ReviewAddForm reviewAddForm, RedirectAttributes redirectAttributes){
        Review review = new Review();
        BeanUtils.copyProperties(reviewAddForm, review);
        review.setBuyerNumber(1l);
        Review save = myPageSVC.save(review);
        log.info("reviewAddForm={}",reviewAddForm);

        Long buyerNumber = save.getBuyerNumber();

        redirectAttributes.addAttribute("id",buyerNumber);

        return "redirect:/mypage/review/{id}";

    }

    //리뷰 목록
    @GetMapping("/review/{id}")
    public String myReview(@PathVariable("id") Long memNumber, Model model){
        List<Review> reviews = myPageSVC.findByMemNumber(memNumber);

        List<Review> list = new ArrayList<>();
        reviews.stream().forEach(review->{
            BeanUtils.copyProperties(review, new ReviewInfoForm());
            list.add(review);
        });
        log.info("list={}",list);
        model.addAttribute("list",list);

        return "mypage/myReview";
    }

    //리뷰 수정 화면
    @GetMapping("/review/edit/{reviewNumber}")
    public String reviewEditForm(@PathVariable("reviewNumber") Long reviewNumber, Model model){
        Optional<Review> foundReview = myPageSVC.findByReviewNumber(reviewNumber);
        ReviewUpdateForm reviewUpdateForm = new ReviewUpdateForm();
        BeanUtils.copyProperties(foundReview.get(),reviewUpdateForm);
        log.info("foundReview={}",foundReview);


        model.addAttribute("form",reviewUpdateForm);

        return "mypage/reviewEdit";
    }

    //리뷰 수정 처리
    @PostMapping("/review/edit/{reviewNumber}")
    public String reviewEdit(@PathVariable("reviewNumber") Long reviewNumber,
                             @ModelAttribute("form") ReviewUpdateForm reviewUpdateForm,
                             RedirectAttributes redirectAttributes){
//        log.info("reviewUpdateForm={}",reviewUpdateForm);
        Review review = new Review();
        BeanUtils.copyProperties(reviewUpdateForm,review);
        //로그인 세션 필요
        reviewUpdateForm.setBuyerNumber(1l);
        review.setBuyerNumber(reviewUpdateForm.getBuyerNumber());

//        log.info("review={}",review);

        Optional<Review> foundReview = myPageSVC.findByReviewNumber(reviewNumber);
//        BeanUtils.copyProperties(foundReview,review);
        Review review1 = foundReview.get();
        Long buyerNumber = review1.getBuyerNumber();
//        log.info("review={}",review);

        myPageSVC.update(reviewNumber, review);

        redirectAttributes.addAttribute("id",buyerNumber);


        return "redirect:/mypage/review/{id}";

    }

    //리뷰 삭제
    @ResponseBody
    @DeleteMapping("/review/del/{reviewNumber}")
    public ApiResponse<Review> reviewDel(@PathVariable("reviewNumber") Long reviewNumber){
        myPageSVC.deleteByReviewId(reviewNumber);


        return ApiResponse.createApiResMsg("00","삭제 성공",null);
    }

    // 프로필 화면
    @GetMapping("/profile/{memNumber}")
    public String profileForm(@PathVariable("memNumber") Long memNumber, Model model){

        ProfileForm profileForm = new ProfileForm();

        Optional<Member> member = myPageSVC.findMember(memNumber);
        Member member1 = member.get();

        List<Review> reviews = myPageSVC.findByBuyerNumber(memNumber);

        List<Review> list = new ArrayList<>();
        reviews.stream().forEach(review -> {
            BeanUtils.copyProperties(review, profileForm);
            list.add(review);
        });

        BeanUtils.copyProperties(member1,profileForm);

        model.addAttribute("list",list);
        model.addAttribute("form",profileForm);
        log.info("profileForm={}",profileForm);
        log.info("list={}",list);

        return "mypage/profile";
    }

    //즐겨찾기 양식
    @GetMapping("/bookmark/{memNumber}")
    public String bookmarkForm(@PathVariable("memNumber") Long memNumber, Model model){

        BookmarkForm bookmarkForm = new BookmarkForm();

        List<Bookmark> bookmarks = myPageSVC.findBookmark(memNumber);

        List<Bookmark> list = new ArrayList<>();
        bookmarks.stream().forEach(bookmark ->  {
            BeanUtils.copyProperties(bookmark,bookmarkForm);
            log.info("bookmarkForm={}",bookmarkForm);
            list.add(bookmark);
        });

        model.addAttribute("list",list);
        log.info("list={}",list);

        return "mypage/bookmark";

    }

    //즐겨찾기 처리
    @ResponseBody
    @PostMapping("/profile/{memNumber}")
    public ApiResponse<Bookmark> bookmark( @PathVariable("memNumber") Long memNumber,@RequestBody BookmarkForm bookmarkForm, Model model){

        Bookmark bookmark = new Bookmark();

        Optional<Member> member = myPageSVC.findMember(memNumber);
        Member member1 = member.get();

        bookmark.setBuyerNumber(1l);
        bookmark.setSellerNumber(member1.getMemNumber());

        myPageSVC.addBookmark(bookmark);

        model.addAttribute("form",bookmarkForm);
        return ApiResponse.createApiResMsg("00","성공",bookmark);
    }

    //프로필에서 삭제
    @ResponseBody
    @DeleteMapping("/profile/del/{memNumber}")
        public  ApiResponse<Bookmark> delBookmark(@PathVariable("memNumber") Long memNumber){
        Optional<Member> member = myPageSVC.findMember(memNumber);
        Member foundMember = member.get();
        myPageSVC.delBookmark(foundMember.getMemNumber());

        return ApiResponse.createApiResMsg("00","성공",null);

    }
    //내 즐겨찾기에서 삭제
    @ResponseBody
    @DeleteMapping("/del/{bookmarkNumber}")
        public ApiResponse<Bookmark> delBookmarkInMyPage(@PathVariable("bookmarkNumber") Long bookmarkNumber){
        Optional<Bookmark> foundBookmark = myPageSVC.findBookmarkNumber(bookmarkNumber);
        myPageSVC.delBookmarkInMyPage(foundBookmark.get().getBookmarkNumber());

        return ApiResponse.createApiResMsg("00","성공",null);

    }

//    //프로필사진 수정화면
//    @GetMapping("/profile/add")
//    public String profileImgAddForm(Model model){
//        model.addAttribute("form" , new ProfileAddForm());
//        return "mypage/profileAddForm";
//    }
//
//    @PostMapping("/profile/add")
//    public String profileImgAdd(@ModelAttribute("form") ProfileAddForm profileAddForm){
//
//        ProfileForm profile = new ProfileForm();
//        BeanUtils.copyProperties(profileAddForm,profile);
//
//
//    }





}
