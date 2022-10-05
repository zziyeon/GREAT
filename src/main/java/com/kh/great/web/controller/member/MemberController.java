package com.kh.great.web.controller.member;

import com.kh.great.domain.dao.member.Member;
import com.kh.great.domain.svc.member.MemberSVC;
import com.kh.great.web.dto.member.Info;
import com.kh.great.web.dto.member.InfoChk;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberSVC memberSVC;


    //개인정보 조회 및 수정 본인확인 화면
    @GetMapping("/infoChk")
    public String infoChk(Model model) {
        model.addAttribute("infoChk", new InfoChk());

        return "member/infoChk";
    }

    //개인정보 조회 및 수정 본인확인 처리
    @PostMapping("/infoChk")
    public String infoChk(
            @Valid @ModelAttribute("infoChk") InfoChk infoChk,
            BindingResult bindingResult,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
    ) {
        HttpSession session = request.getSession(false);
        Object memNum = session.getAttribute("memNumber");
        Object memType = session.getAttribute("memType");

        redirectAttributes.addAttribute("memNum", memNum);
        redirectAttributes.addAttribute("memType", memType);

        //기본 검증
        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "member/infoChk";
        }

        Member findedMember = memberSVC.findByMemNumber((Long) memNum);

        //필드 검증(field error)
        //비밀번호 일치해야 개인정보 조회 가능
        if (!(infoChk.getMemPassword().equals(findedMember.getMemPassword()))) {
            bindingResult.rejectValue("memPassword",null, "비밀번호가 일치하지 않습니다.");
            return "member/infoChk";
        }

        return "redirect:/member/{memType}/{memNum}";
    }

    //고객회원정보 조회 및 수정 화면
    @GetMapping("/customer/{memNumber}")
    public String infoCust(
            @PathVariable("memNumber") Long memNumber,
            Model model
    ){
        Member findedMember = memberSVC.findByMemNumber(memNumber);

        Info info = new Info();
        info.setMemNumber(findedMember.getMemNumber());
        info.setMemType(findedMember.getMemType());
        info.setMemId(findedMember.getMemId());
        info.setMemPassword(findedMember.getMemPassword());
        info.setMemName(findedMember.getMemName());
        info.setMemNickname(findedMember.getMemNickname());
        info.setMemEmail(findedMember.getMemEmail());

        model.addAttribute("info", info);
        return "member/infoCust"; //회원 수정화면
    }

    //점주회원정보 조회 및 수정 화면
    @GetMapping("/owner/{memNumber}")
    public String infoOwn(
            @PathVariable("memNumber") Long memNumber,
            Model model
    ){
        Member findedMember = memberSVC.findByMemNumber(memNumber);

        Info info = new Info();
        info.setMemNumber(findedMember.getMemNumber());
        info.setMemType(findedMember.getMemType());
        info.setMemId(findedMember.getMemId());
        info.setMemPassword(findedMember.getMemPassword());
        info.setMemName(findedMember.getMemName());
        info.setMemNickname(findedMember.getMemNickname());
        info.setMemEmail(findedMember.getMemEmail());
        info.setMemBusinessnumber(findedMember.getMemBusinessnumber());
        info.setMemStoreName(findedMember.getMemStoreName());
        info.setMemStorePhonenumber(findedMember.getMemStorePhonenumber());
        info.setMemStoreLocation(findedMember.getMemStoreLocation());
        info.setMemStoreLatitude(findedMember.getMemStoreLatitude());
        info.setMemStoreLongitude(findedMember.getMemStoreLongitude());
        info.setMemStoreIntroduce(findedMember.getMemStoreIntroduce());
        info.setMemStoreSns(findedMember.getMemStoreSns());
        log.info("확인 : {}",info);

        model.addAttribute("info", info);
        return "member/infoOwn"; //회원 수정화면
    }

    //고객회원정보 수정 처리
    @PostMapping("/customer/{memNum}")
    public String editCust(
            @Valid @ModelAttribute("info") Info info,
            BindingResult bindingResult,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
    ) {
        //검증
        if (bindingResult.hasErrors()){
            log.info("bindingResult={}", bindingResult);
            return "member/infoCust";
        }

        Member member = new Member();
        member.setMemId(info.getMemId());
        member.setMemPassword(info.getMemPassword());
        member.setMemName(info.getMemName());
        member.setMemNickname(info.getMemNickname());
        member.setMemEmail(info.getMemEmail());

        HttpSession session = request.getSession(false);
        Object memNum = session.getAttribute("memNumber");
        Object memType = session.getAttribute("memType");

        redirectAttributes.addAttribute("memNum", memNum);
        redirectAttributes.addAttribute("memType", memType);

        //수정시 세션의 닉네임 변경
        session.setAttribute("memNickname", info.getMemNickname());

        Long updatedRow = memberSVC.update((Long) memNum, member);
        if (updatedRow == 0) {
            return "member/infoCust";
        }

        return "redirect:/member/customer/{memNum}";
    }

    //점주회원정보 수정 처리
    @PostMapping("/owner/{memNum}")
    public String editOwn(
            @Valid @ModelAttribute("info") Info info,
            BindingResult bindingResult,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
    ) {
        //검증
        if(bindingResult.hasErrors()){
            log.info("bindingResult={}", bindingResult);
            return "member/infoOwn";
        }

        Member member = new Member();
        member.setMemId(info.getMemId());
        member.setMemPassword(info.getMemPassword());
        member.setMemName(info.getMemName());
        member.setMemNickname(info.getMemNickname());
        member.setMemEmail(info.getMemEmail());
        member.setMemBusinessnumber(info.getMemBusinessnumber());
        member.setMemStoreName(info.getMemStoreName());
        member.setMemStorePhonenumber(info.getMemStorePhonenumber());
        member.setMemStoreLocation(info.getMemStoreLocation());
        member.setMemStoreLatitude(info.getMemStoreLatitude());
        member.setMemStoreLongitude(info.getMemStoreLongitude());
        member.setMemStoreIntroduce(info.getMemStoreIntroduce());
        member.setMemStoreSns(info.getMemStoreSns());

        HttpSession session = request.getSession(false);
        Object memNum = session.getAttribute("memNumber");
        Object memType = session.getAttribute("memType");

        redirectAttributes.addAttribute("memNum", memNum);
        redirectAttributes.addAttribute("memType", memType);

        //수정시 세션의 닉네임 변경
        session.setAttribute("memNickname", info.getMemNickname());

        Long updatedRow = memberSVC.update((Long) memNum, member);
        if (updatedRow == 0) {
            return "member/infoOwn";
        }

        return "redirect:/member/owner/{memNum}";
    }
}
