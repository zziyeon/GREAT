package com.kh.great.web.dto.mypage;

import com.kh.great.domain.dao.uploadFile.UploadFile;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class GoodForm {

    private Long goodNumber;        //    GOOD_NUMBER	NUMBER(10,0)
    private Long memNumber;    //    MEM_NUMBER	NUMBER(6,0)
    private Long pNumber;    //    P_NUMBER	NUMBER(5,0)

    ///파일 첨부
    private MultipartFile file; //상품설명 첨부파일(단건)
    private List<MultipartFile> files; //상품 이미지 첨부(여러건)

    ///파일 참조
    private List<UploadFile> imageFiles;
}
