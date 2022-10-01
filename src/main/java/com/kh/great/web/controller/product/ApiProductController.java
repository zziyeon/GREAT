package com.kh.great.web.controller.product;

import com.kh.great.domain.common.file.AttachCode;
import com.kh.great.domain.common.file.UploadFileSVC;
import com.kh.great.domain.dao.product.Product;
import com.kh.great.domain.svc.product.ProductSVC;
import com.kh.great.web.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApiProductController {
    private final ProductSVC productSVC;
    private final UploadFileSVC uploadFileSVC;

    // 최신순 목록 GET /api/zonning/recentList
    @GetMapping("/zonning/recentList")
    public ApiResponse<List<Product>> recentList(@RequestParam("zone") String zone){
        log.info("zone={}", zone);

        List<Product> list = productSVC.recentList(zone);

        for (int i = 0; i < list.size(); i++) {
            list.get(i).setImageFiles(uploadFileSVC.getFilesByCodeWithRid(
                    AttachCode.P0102.name(),
                    list.get(i).getPNumber()));
        }

        log.info("list={}", list);
        //api 응답 메시지
        return ApiResponse.createApiResMsg("00", "성공", list);

    }

    // 높은 할인순순 목록 GET /api/zonning/discountListDesc
    @GetMapping("/zonning/discountListDesc")
    public ApiResponse<List<Product>> discountListDesc(@RequestParam("zone") String zone){
        List<Product> list = productSVC.discountListDesc(zone);

        for (int i = 0; i < list.size(); i++) {
            list.get(i).setImageFiles(uploadFileSVC.getFilesByCodeWithRid(
                    AttachCode.P0102.name(),
                    list.get(i).getPNumber()));
        }
        //api 응답 메시지
        return ApiResponse.createApiResMsg("00", "성공", list);
    }

    // 낮은 가격순 목록 GET /api/zonning/priceList
    @GetMapping("/zonning/priceList")
    public ApiResponse<List<Product>> priceList(@RequestParam("zone") String zone){
        List<Product> list = productSVC.priceList(zone);

        for (int i = 0; i < list.size(); i++) {
            list.get(i).setImageFiles(uploadFileSVC.getFilesByCodeWithRid(
                    AttachCode.P0102.name(),
                    list.get(i).getPNumber()));
        }
        //api 응답 메시지
        return ApiResponse.createApiResMsg("00", "성공", list);
    }

    // 높은 가격순 목록 GET /api/zonning/priceListDesc
    @GetMapping("/zonning/priceListDesc")
    public ApiResponse<List<Product>> priceListDesc(@RequestParam("zone") String zone){
        List<Product> list = productSVC.priceListDesc(zone);

        for (int i = 0; i < list.size(); i++) {
            list.get(i).setImageFiles(uploadFileSVC.getFilesByCodeWithRid(
                    AttachCode.P0102.name(),
                    list.get(i).getPNumber()));
        }
        //api 응답 메시지
        return ApiResponse.createApiResMsg("00", "성공", list);
    }

    // 날짜로 상품관리 목록 조회
    @GetMapping("/manage/{ownerNumber}")
    public ApiResponse<List<Product>> manageByDate(@PathVariable("ownerNumber") Long ownerNumber, Model model) {
        List<Product> list = productSVC.pManage(ownerNumber);
        model.addAttribute("list", list);

        //api 응답 메시지
        return ApiResponse.createApiResMsg("00", "성공", list);
    }
    //---------------------------------------------------------------------------------------------------------------
    // 한식 카테고리
    // 높은 가격순 목록 GET /api/zonning/priceListDesc
    @GetMapping("/zonning/kFood")
    public ApiResponse<List<Product>> kFood(){
        List<Product> list = productSVC.kFood();

        for (int i = 0; i < list.size(); i++) {
            list.get(i).setImageFiles(uploadFileSVC.getFilesByCodeWithRid(
                    AttachCode.P0102.name(),
                    list.get(i).getPNumber()));
        }
        //api 응답 메시지
        return ApiResponse.createApiResMsg("00", "성공", list);
    }
}
