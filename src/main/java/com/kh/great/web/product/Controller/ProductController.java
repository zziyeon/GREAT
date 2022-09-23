package com.kh.great.web.product.Controller;

import com.kh.great.domain.Product;
import com.kh.great.domain.svc.product.ProductSVC;
import com.kh.great.web.product.dto.DetailForm;
import com.kh.great.web.product.dto.SaveForm;
import com.kh.great.web.product.dto.UpdateForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/great/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductSVC productSVC;

    //등록 양식
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("form", new SaveForm());

        return "product/addForm";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("form") SaveForm saveForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        Product product = new Product();
        System.out.println(saveForm.toString());
        BeanUtils.copyProperties(saveForm, product);

        System.out.println("product -> " + product);
        Product savedProduct = productSVC.save(product);

        Long num = savedProduct.getPNumber();
        redirectAttributes.addAttribute("num", num);
        return "redirect:/great/products/{num}";
    }

    //상품 개별 조회
    @GetMapping("/{num}")
    public String findByProductNum(@PathVariable("num") Long num, Model model) {
        Product findedProduct = productSVC.findByProductNum(num);
        DetailForm detailForm = new DetailForm();
        BeanUtils.copyProperties(findedProduct, detailForm);

        model.addAttribute("form", detailForm);

        return "product/detailForm";
    }

    //수정 화면
    @GetMapping("/{num}/edit")
    public String editFrom(@PathVariable("num") Long num, Model model) {
        Product findedProduct = productSVC.findByProductNum(num);

        UpdateForm updateForm = new UpdateForm();
        BeanUtils.copyProperties(findedProduct, updateForm);

        model.addAttribute("form", updateForm);

        return "product/updateForm";
    }

    //수정 처리
    @PostMapping("/{num}/edit")
    public String edit(@PathVariable("num") Long num, @Valid @ModelAttribute("form") UpdateForm updateForm, BindingResult bindingResult) {
        Product product = new Product();
        BeanUtils.copyProperties(updateForm, product);

        int updatedRow = productSVC.update(num, product);
        if (updatedRow == 0) {
            return "product/updateForm";
        }
        return "redirect:/great/products/{num}";
    }

    //삭제처리
    @GetMapping("/{num}/del")
    public String delete(@PathVariable("num") Long num) {
        int deletedRow = productSVC.deleteByProductNum(num);
        if (deletedRow == 0) {
            return "redirect:/products/" + num;
        }
        return "redirect:/great/products";
    }
    //상품관리
    @GetMapping("/{ownerNumber}/manage")
    public String manage(@PathVariable("ownerNumber") Long ownerNumber, Model model) {
        List<Product> list = productSVC.pManage(ownerNumber);
        model.addAttribute("list", list);

        return "product/manage";
    }
}
