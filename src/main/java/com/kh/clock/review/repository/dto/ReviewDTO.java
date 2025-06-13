package com.kh.clock.review.repository.dto;

import java.sql.Date;
import lombok.Data;

@Data
public class ReviewDTO {
  private int revSq;
  private double revSco;
  private String ckRevSt;
  private String revCont;
  private Date revRegDt;
  private String resCd;
  private int accomSq;
}
