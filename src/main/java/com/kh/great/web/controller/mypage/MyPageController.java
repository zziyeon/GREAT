package com.kh.great.web.controller.mypage;

import com.kh.great.domain.common.file.AttachCode;
import com.kh.great.domain.dao.deal.Deal;
import com.kh.great.domain.dao.member.Member;
import com.kh.great.domain.dao.mypage.Bookmark;
import com.kh.great.domain.dao.mypage.Good;
import com.kh.great.domain.dao.mypage.Review;
import com.kh.great.domain.dao.product.Product;
import com.kh.great.domain.svc.deal.DealSVC;
import com.kh.great.domain.svc.mypage.MyPageSVC;
import com.kh.great.domain.svc.product.ProductSVC;
import com.kh.great.domain.svc.uploadFile.UploadFileSVC;
import com.kh.great.web.api.ApiResponse;
import com.kh.great.web.dto.mypage.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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
    private final UploadFileSVC uploadFileSVC;

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
    @GetMapping("/review/add/{orderNumber}")
    public String saveForm(@PathVariable("orderNumber") Long orderNumber, Model model){
//        Optional<Deal> foundOrderNumber = dealSVC.findByOrderNumber(orderNumber);
//        Deal deal = foundOrderNumber.get();


        ReviewAddForm reviewAddForm = new ReviewAddForm();

//        BeanUtils.copyProperties(deal,reviewAddForm);

        log.info("reviewAddForm={}",reviewAddForm);
        model.addAttribute("form",reviewAddForm);
        return "mypage/reviewAdd";
    }

    //리뷰 등록 처리
    @PostMapping("/review/add/{orderNumber}")
    public String save(@PathVariable("orderNumber") Long orderNumber,
            @ModelAttribute("form") ReviewAddForm reviewAddForm,
                       RedirectAttributes redirectAttributes,
                       HttpServletRequest request){

        Optional<Deal> foundOrderNumber = dealSVC.findByOrderNumber(orderNumber);
        Deal deal = foundOrderNumber.get();

        Review review = new Review();
        BeanUtils.copyProperties(reviewAddForm, review);
        BeanUtils.copyProperties(deal,review);
        log.info("reviewAddForm={}",reviewAddForm);
        log.info("deal={}",deal);

        HttpSession session = request.getSession(false);
        Object memNumber = session.getAttribute("memNumber");
        review.setBuyerNumber((Long)memNumber);
        review.setSellerNumber(deal.getSellerNumber());

        Review save = myPageSVC.save(review);
        log.info("reviewAddForm={}",reviewAddForm);

        Long buyerNumber = save.getBuyerNumber();

        redirectAttributes.addAttribute("id",buyerNumber);

        return "redirect:/mypage/review/{id}";

    }

    //리뷰 목록
    @GetMapping("/review/{id}")
    public String myReview(@PathVariable("id") Long memNumber, Model model){
        ReviewInfoForm reviewInfoForm = new ReviewInfoForm();
        Optional<Member> member = myPageSVC.findMember(memNumber);
        Member member1 = member.get();
        MemberForm memberForm = new MemberForm();
        BeanUtils.copyProperties(member1,memberForm);

        List<Review> reviews = myPageSVC.findByMemNumber(memNumber);

        List<Review> list = new ArrayList<>();
        reviews.stream().forEach(review->{
            BeanUtils.copyProperties(review, reviewInfoForm);
            list.add(review);
        });
        log.info("list={}",list);
        log.info("ReviewInfoForm={}",reviewInfoForm);
        model.addAttribute("list",list);
        model.addAttribute("form",memberForm);

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
                             RedirectAttributes redirectAttributes,
                             HttpServletRequest request){
//        log.info("reviewUpdateForm={}",reviewUpdateForm);
        Review review = new Review();
        BeanUtils.copyProperties(reviewUpdateForm,review);
        //로그인 세션 필요
        HttpSession session = request.getSession(false);
        Object memNumber = session.getAttribute("memNumber");
        reviewUpdateForm.setBuyerNumber((Long)memNumber);
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
        //회원 조회
        Optional<Member> member = myPageSVC.findMember(memNumber);
        Member member1 = member.get();

        //리뷰조회
        List<Review> reviews = myPageSVC.findBySellerNumber(memNumber);

        List<Review> list = new ArrayList<>();
        reviews.stream().forEach(review -> {
            BeanUtils.copyProperties(review, profileForm);
            list.add(review);
        });

        //판매글 조회
        List<Product> products = myPageSVC.findByOwnerNumber(memNumber);

        List<Product> list2 = new ArrayList<>();
        products.stream().forEach(product -> {
            BeanUtils.copyProperties(product,profileForm);
            list2.add(product);
        for (int i = 0; i < list2.size(); i++) {
            list2.get(i).setImageFiles(uploadFileSVC.getFilesByCodeWithRid(
                    AttachCode.P0102.name(),
                    list2.get(i).getPNumber()));
            }
        });


        //판매번호 조회


        //첨부파일 조회
//        List<UploadFile> uploadFiles = uploadFileSVC.getFilesByCodeWithRid(AttachCode.P0102.name(), profileForm.getPNumber());
//        if(uploadFiles.size() > 0 ){
//            profileForm.setImageFiles(uploadFiles);
//        }


        BeanUtils.copyProperties(member1,profileForm);


        model.addAttribute("list",list);
        model.addAttribute("list2",list2);
        model.addAttribute("form",profileForm);
        log.info("profileForm={}",profileForm);
        log.info("list={}",list);
        log.info("list2={}",list2);

        return "mypage/profile";
    }

    //즐겨찾기 양식
    @GetMapping("/bookmark/{memNumber}")
    public String bookmarkForm(@PathVariable("memNumber") Long memNumber, Model model){

        Optional<Member> member = myPageSVC.findMember(memNumber);
        Member member1 = member.get();
        MemberForm memberForm = new MemberForm();
        BeanUtils.copyProperties(member1,memberForm);

        BookmarkForm bookmarkForm = new BookmarkForm();

        List<Bookmark> bookmarks = myPageSVC.findBookmark(memNumber);

        List<Bookmark> list = new ArrayList<>();
        bookmarks.stream().forEach(bookmark ->  {
            BeanUtils.copyProperties(bookmark,bookmarkForm);
            log.info("bookmarkForm={}",bookmarkForm);
            list.add(bookmark);
        });

        model.addAttribute("list",list);
        model.addAttribute("form",memberForm);
        log.info("list={}",list);

        return "mypage/bookmark";

    }

    //즐겨찾기 처리
    @ResponseBody
    @PostMapping("/profile/{memNumber}")
    public ApiResponse<Bookmark> bookmark( @PathVariable("memNumber") Long memNumber,
                                           @RequestBody BookmarkForm bookmarkForm,
                                           HttpServletRequest request,
                                           Model model){

        Bookmark bookmark = new Bookmark();

        Optional<Member> member = myPageSVC.findMember(memNumber);
        Member member1 = member.get();

        HttpSession session = request.getSession(false);
        Object foundMemNumber = session.getAttribute("memNumber");

        bookmark.setBuyerNumber((Long)foundMemNumber);
        bookmark.setSellerNumber(member1.getMemNumber());

        myPageSVC.addBookmark(bookmark);

        model.addAttribute("form",bookmarkForm);
        return ApiResponse.createApiResMsg("00","성공",bookmark);
    }

    //프로필에서 삭제
    @ResponseBody
    @DeleteMapping("/profile/del/{memNumber}")
        public  ApiResponse<Bookmark> delBookmark(@PathVariable("memNumber") Long memNumber){
//        Optional<Member> member = myPageSVC.findMember(memNumber);
//        Member foundMember = member.get();
        myPageSVC.delBookmark(memNumber);

        return ApiResponse.createApiResMsg("00","성공",null);
    }

    //내 즐겨찾기에서 삭제
    @ResponseBody
    @DeleteMapping("/del/{bookmarkNumber}")
        public ApiResponse<Bookmark> delBookmarkInMyPage(@PathVariable("bookmarkNumber") Long bookmarkNumber){
        myPageSVC.delBookmarkInMyPage(bookmarkNumber);

        return ApiResponse.createApiResMsg("00","성공",null);
    }

    //프로필사진 수정화면
    @GetMapping("/profile/add")
    public String profileImgAddForm(@Valid @ModelAttribute("form") SaveForm saveForm, Model model){


        model.addAttribute("form" , new ProfileAddForm());
        return "mypage/profileAddForm";
    }
//
//    @PostMapping("/profile/add")
//    public String profileImgAdd(@ModelAttribute("form") ProfileAddForm profileAddForm){
//
//        ProfileForm profile = new ProfileForm();
//        BeanUtils.copyProperties(profileAddForm,profile);
//
//
//    }


    //좋아요 양식
    @GetMapping("/good/{memNumber}")
    public String goodForm(@PathVariable("memNumber") Long memNumber, Model model){

        Optional<Member> member = myPageSVC.findMember(memNumber);
        Member member1 = member.get();
        MemberForm memberForm = new MemberForm();
        BeanUtils.copyProperties(member1,memberForm);

        GoodForm goodForm = new GoodForm();

        List<Good> goods = myPageSVC.findGoods(memNumber);

        List<Good> list = new ArrayList<>();
        goods.stream().forEach(good ->  {
            BeanUtils.copyProperties(good,goodForm);
            log.info("goodForm={}",goodForm);
            list.add(good);
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setImageFiles(uploadFileSVC.getFilesByCodeWithRid(
                        AttachCode.P0102.name(),
                        list.get(i).getPNumber()));
            }
        });



        model.addAttribute("list",list);
        model.addAttribute("form",memberForm);
        model.addAttribute("goods",goodForm);
        log.info("list={}",list);

        return "mypage/good";
    }

    //좋아요 추가
    @ResponseBody
    @PostMapping("/good/{pNumber}")
    public ApiResponse<Good> good( @PathVariable("pNumber") Long pNumber,
                                           @RequestBody GoodForm goodForm,
                                           HttpServletRequest request,
                                           Model model){

        Good good = new Good();

        HttpSession session = request.getSession(false);
        Object foundMemNumber = session.getAttribute("memNumber");

        good.setMemNumber((Long)foundMemNumber);
        good.setPNumber(pNumber);

        myPageSVC.addGood(good);

        model.addAttribute("form",goodForm);
        return ApiResponse.createApiResMsg("00","성공",good);
    }

    //프로필에서 삭제
    @ResponseBody
    @DeleteMapping("/good/del/{pNumber}")
    public  ApiResponse<Good> delGood(@PathVariable("pNumber") Long pNumber){

        myPageSVC.delGood(pNumber);

        return ApiResponse.createApiResMsg("00","좋아요 삭제 성공",null);
    }
    //내 좋아요에서 삭제
    @ResponseBody
    @DeleteMapping("mygood/del/{goodNumber}")
    public ApiResponse<Good> delGoodInMyPage(@PathVariable("goodNumber") Long goodNumber){
        myPageSVC.delGoodInMyPage(goodNumber);

        return ApiResponse.createApiResMsg("00","성공",null);
    }

}
