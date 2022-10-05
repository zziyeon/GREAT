package com.kh.great.web.dto.mypage;

import com.kh.great.domain.dao.member.Member;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewUpdateForm {
    private Long reviewNumber;
    private Long buyerNumber;
    private String content;
    private Long grade;
    private LocalDateTime writeDate;
    private Long profileNumber;
    private Member member;

}
