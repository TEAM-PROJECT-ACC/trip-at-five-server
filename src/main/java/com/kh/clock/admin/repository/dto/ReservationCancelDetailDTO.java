package com.kh.clock.admin.repository.dto;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationCancelDetailDTO {
  private String receiptId;
  private String resCd;
  private String resEmailId;
  private String resName;
  private String resPhone;
  private int resNumOfPeo;
  private String checkInDt;
  private String checkOutDt;
  private Date resRegDt;
  private String roomName;
  private String accomName;
}
