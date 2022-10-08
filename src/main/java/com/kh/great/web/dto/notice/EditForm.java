package com.kh.great.web.dto.notice;


import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class EditForm {
  private Long noticeId;
  @Length(min = 1,max = 50)
  private String title;
  @Length(min = 1, max = 500)
  private String content;
  private String attachments;
  private String write;
}
