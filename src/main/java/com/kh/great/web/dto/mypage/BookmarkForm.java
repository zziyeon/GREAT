package com.kh.great.web.dto.mypage;

import lombok.Data;

@Data
public class BookmarkForm {
    private Long bookmarkNumber;
    private Long buyerNumber;
    private Long sellerNumber;
    private Member member;
}
