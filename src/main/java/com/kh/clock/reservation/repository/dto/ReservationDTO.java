package com.kh.clock.reservation.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {
  private String resEmail;
  private String resName;
  private String resPhone;
  private String resCode;
  private String checkInDt;
  private String checkOutDt;
  private int resNumOfPeo;
  private int roomInfo;
  private int memNo;
}
