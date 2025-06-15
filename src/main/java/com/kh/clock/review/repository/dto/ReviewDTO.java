package com.kh.clock.review.repository.dto;

import java.sql.Date;
import java.util.List;
import com.kh.clock.reservation.repository.dto.ReservationDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor 
public class ReviewDTO {
  private int revSq;
  private double revSco;
  private String ckRevSt;
  private String revCont;
  private Date revRegDt;

  private String resCd;
  private int accomSq;  
  
  private String memNick;    // 회원 닉네임
  private int roomNo;        // 객실번호
  private int accomNo;       // 숙박업소번호
  
  private List<ReviewImageDTO> imageList;
  private ReservationDTO reservation;

}
