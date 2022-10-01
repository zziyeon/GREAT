package com.kh.great.web.dto.member;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class FindPw {
  @NotBlank
  private String memId;
  @NotBlank
  private String memEmail;
}