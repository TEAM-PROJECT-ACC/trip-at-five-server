package com.kh.clock.reservation.domain;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationVO {
  private String resCd;
  private String resEmailId;
  private String resName;
  private String resPhone;
  private int resNumOfPeo;
  private Date checkInDt;
  private Date checkOutDt;
  private String ckResSt;
  private Date resRegDT;
  private int roomNo;
  private int memNo;

  public ReservationVO(String resCd, String resEmailId, String resName, String resPhone,
      int resNumOfPeo, Date checkInDt, Date checkOutDt, int roomNo, int memNo) {
    this.resCd = resCd;
    this.resEmailId = resEmailId;
    this.resName = resName;
    this.resPhone = resPhone;
    this.resNumOfPeo = resNumOfPeo;
    this.checkInDt = checkInDt;
    this.checkOutDt = checkOutDt;
    this.roomNo = roomNo;
    this.memNo = memNo;
  }
}
