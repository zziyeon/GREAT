package com.kh.great.web.dto.deal;

import com.kh.great.domain.dao.uploadFile.UploadFile;
import com.kh.great.domain.dao.member.Member;
import com.kh.great.domain.dao.product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddForm {
    private Long orderNumber;      //order_number number(6),  --주문번호
    private Long buyerNumber;      //    buyer_number number (6), --구매자번호
    private Long sellerNumber;        //    seller_number number (6),--핀매자번호
    private Long pNumber;      //    p_number number (5),   --판매번호
    private Long pCount;      //    p_count number (3),      --상품수량
    private Long price;      //    price number (6),        --가격
    private String visittime;      //    visittime date,          --방문예정시간
    private Long buyType;      //    buy_type number(1) default 0,     --결제유형
    private Long rStatus;      //    r_status number(1) default 0,      --리뷰상태
    private Long oStatus;      //    o_status number(1) default 0,      --주문상태
    private Date orderdate;      //    orderdate date,          --주문일자
    private Long pickupStatus;      //    pickup_status number (1) default 0 --픽업상태
    private Product product;
    private Member member;
        private Long ownerNumber;       //점주고객번호    OWNER_NUMBER	NUMBER(6,0)
    private String memStoreName;       //가게명
    private String pTitle;          //상품 제목    P_TITLE	VARCHAR2(90 BYTE)
    private String pName;           //상품명    P_NAME	VARCHAR2(60 BYTE)
    //    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private String deadlineTime;    //마감일자    DEADLINE_TIME	DATE
        private String pCategory;      //업종카테고리    CATEGORY	VARCHAR2(17 BYTE)
    private Integer totalCount;     //총수량    TOTAL_COUNT	NUMBER(5,0)
    private Integer remainCount;    //남은 수량    REMAIN_COUNT	NUMBER(5,0)
    private Integer normalPrice;    //정상가    NORMAL_PRICE	NUMBER(8,0)
    private Integer salePrice;      //할인가    SALE_PRICE	NUMBER(8,0)
    private Integer discountRate;   //할인율    DISCOUNT_RATE	NUMBER(2,0)
    private String paymentOption;  //결제방식    PAYMENT_OPTION	VARCHAR2 (32 BYTE)
    private String detailInfo;     //상품설명    DETAIL_INFO	VARCHAR2 (4000 BYTE)

    ///파일 첨부
    private MultipartFile file; //상품설명 첨부파일(단건)
    private List<MultipartFile> files; //상품 이미지 첨부(여러건)

    ///파일 참조
    private List<UploadFile> imageFiles;


}
