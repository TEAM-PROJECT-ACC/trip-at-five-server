package com.kh.clock.review.domain;

import java.sql.Date;
import lombok.Data;

/**
REV_SQ      이용후기번호
REV_SCO     평점
CK_REV_ST   공개여부설정
REV_CONT    후기 내용
REV_REG_DT  등록일
RES_CD      예약코드
 */
@Data
public class ReviewVO {
  private int revSq;
  private double revSco;
  private String ckRevSt;
  private String revCont;
  private Date revRegDt;
  private String resCd;
  private int memNo;
  private int accomSq;
}
