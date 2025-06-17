package com.kh.clock.admin.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminReservationCancelListDTO {
  private String no;
  private String receiptId;
  private String resName;
  private int roomPrice;
  private String ckResSt;
}
