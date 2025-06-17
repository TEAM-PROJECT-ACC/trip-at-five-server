package com.kh.clock.admin.repository.dto;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminReservationDetailDTO {
  private String resCd;
  private String resName;
  private String resPhone;
  private String resEmailId;
  private String accomTypeName;
  private String accomName;
  private String roomName;
  private String receiptId;
  private String payMethod;
  private int payPrice;
  private Date payDt;
}
