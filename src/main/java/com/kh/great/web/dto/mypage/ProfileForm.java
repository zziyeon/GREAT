package com.kh.great.web.dto.mypage;

import com.kh.great.domain.dao.uploadFile.UploadFile;
import com.kh.great.domain.dao.mypage.Bookmark;
import com.kh.great.domain.dao.product.Product;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProfileForm {
    private Long memNumber;                   //varchar2(9)
    private String memType;                     //varchar2(15)
    private String memId;                       //varchar2(30)
    private String memPassword;                 //varchar2(18)
    private String memName;                     //varchar2(18)
    private String memNickname;                 //varchar2(18)
    private String memEmail;                    //varchar2(30)
    private String memBusinessnumber;           //varchar2(10)
    private String memStoreName;                //varchar2(45)
    private String memStorePhonenumber;         //varchar2(15)
    private String memStoreLocation;            //varchar2(150)
    private String memStoreLatitude;            //varchar2(15, 9)
    private String memStoreLongitude;           //varchar2(15, 9)
    private String memStoreIntroduce;           //varchar2(150)
    private String memStoreSns;                 //varchar2(150)
    private LocalDateTime memRegtime;           //date
    private LocalDateTime memLockExpiration;    //date
    private String memAdmin;                    //varchar2(3)
    private Product product;
    private Bookmark bookmark;
    private Long pNumber;
    private Long ownerNumber;
    private String deadlineTime;
    private String pTitle;
    private String detailInfo;

    ///파일 첨부
    private MultipartFile file; //상품설명 첨부파일(단건)
    private List<MultipartFile> files; //상품 이미지 첨부(여러건)

    ///파일 참조
    private List<UploadFile> imageFiles;
}
