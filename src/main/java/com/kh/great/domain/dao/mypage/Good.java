package com.kh.great.domain.dao.mypage;

import com.kh.great.domain.dao.product.Product;
import lombok.Data;

@Data
public class Good {
    private Long goodNumber;        //    GOOD_NUMBER	NUMBER(10,0)
    private Long memNumber;    //    MEM_NUMBER	NUMBER(6,0)
    private Long pNumber;    //    P_NUMBER	NUMBER(5,0)
    private Product product;
}
