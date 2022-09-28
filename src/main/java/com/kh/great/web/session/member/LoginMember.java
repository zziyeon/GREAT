package com.kh.great.web.session.member;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginMember {
    private Long memNumber;
    private String memType;
    private String memId;
    private String memNickname;
    private String memStoreName;
}
