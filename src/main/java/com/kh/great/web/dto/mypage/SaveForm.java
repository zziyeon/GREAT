package com.kh.great.web.dto.mypage;

import com.kh.great.domain.dao.member.Member;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Data
public class SaveForm {
  private Member member;
  private List<MultipartFile> files;    //상품 이미지 첨부(여러건)
}
