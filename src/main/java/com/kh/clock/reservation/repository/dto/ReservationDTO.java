package com.kh.clock.reservation.repository.dto;

import java.sql.Date;
import java.util.List;
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
  private Date checkInDt;
  private Date checkOutDt;
  private int resNumOfPeo;
  private List<Integer> roomInfo;
  private int memNo;
}
