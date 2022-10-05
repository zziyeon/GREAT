package com.kh.great.domain.dao.mypage;

import lombok.Data;

@Data
public class Bookmark {
    private Long bookmarkNumber;
    private Long buyerNumber;
    private Long sellerNumber;
    private Member member;
}
