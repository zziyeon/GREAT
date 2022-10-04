package com.kh.great.web.controller.product;

import com.kh.great.domain.common.file.AttachCode;
import com.kh.great.domain.common.file.UploadFileSVC;
import com.kh.great.domain.dao.product.Product;
import com.kh.great.domain.svc.product.ProductSVC;
import com.kh.great.web.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApiProductController {
    private final ProductSVC productSVC;
    private final UploadFileSVC uploadFileSVC;

    // 최신순 목록 GET /api/zonning/recentList
    @GetMapping("/zonning/recentList")
    public ApiResponse<List<Product>> recentList(@RequestParam Map<String, Object> allParameters){
        String zone = allParameters.get("zone").toString();
        String category = allParameters.get("category").toString();

        List<Product> list = productSVC.recentList(allParameters);

        for (int i = 0; i < list.size(); i++) {
            list.get(i).setImageFiles(uploadFileSVC.getFilesByCodeWithRid(
                    AttachCode.P0102.name(),
                    list.get(i).getPNumber()));
        }
//        //api 응답 메시지
        return ApiResponse.createApiResMsg("00", "성공", list);
    }

    // 높은 할인순 목록 GET /api/zonning/discountListDesc
    @GetMapping("/zonning/discountListDesc")
    public ApiResponse<List<Product>> discountListDesc(@RequestParam Map<String, Object> allParameters){
        String zone = allParameters.get("zone").toString();
        String category = allParameters.get("category").toString();

        List<Product> list = productSVC.discountListDesc(allParameters);

        for (int i = 0; i < list.size(); i++) {
            list.get(i).setImageFiles(uploadFileSVC.getFilesByCodeWithRid(
                    AttachCode.P0102.name(),
                    list.get(i).getPNumber()));
        }
//        //api 응답 메시지
        return ApiResponse.createApiResMsg("00", "성공", list);
    }

    // 낮은 가격순 목록 GET /api/zonning/priceList
    @GetMapping("/zonning/priceList")
    public ApiResponse<List<Product>> priceList(@RequestParam Map<String, Object> allParameters){
        String zone = allParameters.get("zone").toString();
        String category = allParameters.get("category").toString();

        List<Product> list = productSVC.priceList(allParameters);

        for (int i = 0; i < list.size(); i++) {
            list.get(i).setImageFiles(uploadFileSVC.getFilesByCodeWithRid(
                    AttachCode.P0102.name(),
                    list.get(i).getPNumber()));
        }
//        //api 응답 메시지
        return ApiResponse.createApiResMsg("00", "성공", list);
    }

    // 높은 가격순 목록 GET /api/zonning/priceListDesc
    @GetMapping("/zonning/priceListDesc")
    public ApiResponse<List<Product>> priceListDesc(@RequestParam Map<String, Object> allParameters){
        String zone = allParameters.get("zone").toString();
        String category = allParameters.get("category").toString();

        List<Product> list = productSVC.priceListDesc(allParameters);

        for (int i = 0; i < list.size(); i++) {
            list.get(i).setImageFiles(uploadFileSVC.getFilesByCodeWithRid(
                    AttachCode.P0102.name(),
                    list.get(i).getPNumber()));
        }
//        //api 응답 메시지
        return ApiResponse.createApiResMsg("00", "성공", list);
    }

    // 검색 결과 목록 @GET /api/searchresult
    @GetMapping("/searchresult")
    public ApiResponse<List<Product>> searchresult(@RequestParam ("searchKeyword") String searchKeyword){
        List<Product> list = productSVC.search(searchKeyword);

        for (int i = 0; i < list.size(); i++) {
            list.get(i).setImageFiles(uploadFileSVC.getFilesByCodeWithRid(
                    AttachCode.P0102.name(),
                    list.get(i).getPNumber()));
        }

        log.info("list={}", list);
        //api 응답 메시지
        return ApiResponse.createApiResMsg("00", "성공", list);
    }

    // 상품관리
    @GetMapping("/manage/{ownerNumber}")
    public ApiResponse<List<Product>> manageByDate(@PathVariable("ownerNumber") Long ownerNumber, @RequestParam ("sell_status") Integer sell_status, @RequestParam ("history_start_date") String history_start_date, @RequestParam ("history_end_date") String history_end_date) {
        List<Product> list = productSVC.pManage(ownerNumber, sell_status, history_start_date, history_end_date);
        System.out.println("@@@@list = " + list);

        for (int i = 0; i < list.size(); i++) {
            list.get(i).setImageFiles(uploadFileSVC.getFilesByCodeWithRid(
                    AttachCode.P0102.name(),
                    list.get(i).getPNumber()));
        }

//        model.addAttribute("list", list);
        //api 응답 메시지
        System.out.println("ownerNumber = " + ownerNumber + ", history_start_date = " + history_start_date + ", history_end_date = " + history_end_date);
        return ApiResponse.createApiResMsg("00", "성공", list);
    }

//    // 상품관리
//    @GetMapping("/manage/{ownerNumber}")
//    public ApiResponse<List<Product>> manageByDate(@PathVariable("ownerNumber") Long ownerNumber, Model model) {
//        List<Product> list = productSVC.pManage(ownerNumber);
//
//        for (int i = 0; i < list.size(); i++) {
//            list.get(i).setImageFiles(uploadFileSVC.getFilesByCodeWithRid(
//                    AttachCode.P0102.name(),
//                    list.get(i).getPNumber()));
//        }
//
////        model.addAttribute("list", list);
//        //api 응답 메시지
//        return ApiResponse.createApiResMsg("00", "성공", list);
//    }
}
