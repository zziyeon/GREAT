package com.kh.great.web.session;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginMember {
  private Long memNumber;       //회원 번호
  private String memType;       //회원 유형
  private String memNickname;   //회원 닉네임
}
