package com.kh.clock.nonMember.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NonMemberAccomInfo {
  private int accomSq;          //  ACCOM_SQ    NUMBER  숙박업소번호
  private String accomName;     //  ACCOM_NAME  VARCHAR2(100 BYTE)  숙박업소명
  private String accomPhone;    //  ACCOM_PHONE VARCHAR2(100 BYTE)  전화번호
}
