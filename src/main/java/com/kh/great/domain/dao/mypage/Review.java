package com.kh.great.domain.dao.mypage;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Review {
    private Long reviewNumber;            //    REVIEW_NUMBER	NUMBER(1,0)	No		1
    private Long buyerNumber;            //    BUYER_NUMBER	NUMBER(6,0)	Yes		2
    private Long sellerNumber;
    private String content;
    private LocalDateTime writeDate;
    private Long grade;
    private Long profileNumber;
    private Member member;



    //    SELLER_NUMBER	NUMBER(6,0)	Yes		3
            //    CONTENT	VARCHAR2(150 BYTE)	Yes		4
            //    WRITE_DATE	DATE	Yes		5
            //    GRADE	NUMBER(5,0)	Yes		6
}
