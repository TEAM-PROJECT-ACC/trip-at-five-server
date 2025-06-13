package com.kh.clock.reservation.domain;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationVO {
  private String resCode;
  private String resEmail;
  private String resName;
  private String resPhone;
  private int resNumOfPeo;
  private String checkInDt;
  private String checkOutDt;
  private String ckResSt;
  private Date resRegDT;
  private int roomNo;
  private int memNo;

  public ReservationVO(String resCode, String resEmail, String resName, String resPhone,
      int resNumOfPeo, String checkInDt, String checkOutDt, int roomNo, int memNo) {
    this.resCode = resCode;
    this.resEmail = resEmail;
    this.resName = resName;
    this.resPhone = resPhone;
    this.resNumOfPeo = resNumOfPeo;
    this.checkInDt = checkInDt;
    this.checkOutDt = checkOutDt;
    this.roomNo = roomNo;
    this.memNo = memNo;
  }
}
