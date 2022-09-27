package com.kh.great3.web.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class resetPw {
    @NotBlank
    private String memId;                       //varchar2(30)

    @NotBlank
    private String memPassword;                 //varchar2(18)

    @NotBlank
    private String memPasswordCheck;
}
