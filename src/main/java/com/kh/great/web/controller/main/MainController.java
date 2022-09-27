package com.kh.great.web.main;

import com.kh.great.domain.Product;
import com.kh.great.domain.svc.product.ProductSVC;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class MainController {
    final ProductSVC productSVC;

    //홈페이지 메인
    @GetMapping("")
    public String main(Model model) {
        List<Product> list = productSVC.today_deadline();
        model.addAttribute("list", list);

        return "main/main";
    }

    //지역별 상품 목록
    @GetMapping("/zonning")
    @Nullable
    public String zonning(Model model) {
        List<Product> list = productSVC.findAll();
        model.addAttribute("list", list);

        return "main/zonning_list";
    }

//    // 상품 검색
//    public String select(Model model, String findStr) {
//        List<Product> list = productSVC.select(findStr);
//        model.addAttribute("list", list);
//
//        return
//    }
}
