package com.kh.clock.reservation.repository.dto;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResInfo {
  private String resCd;
  private String accomName;
  private String roomName;
  private Date checkInDt;
  private Date checkOutDt;
  private int roomPrice;
}
