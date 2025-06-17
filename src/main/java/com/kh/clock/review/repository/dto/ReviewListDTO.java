package com.kh.clock.review.repository.dto;

import java.sql.Date;
import java.util.List;

public class ReviewListDTO {
  private int revSq;
  private double revSco;
  private String revCont;
  private Date revRegDt;
  // 회원 닉네임
  private String memNick;
  private List<ReviewImageDTO> reviewImages;
}
