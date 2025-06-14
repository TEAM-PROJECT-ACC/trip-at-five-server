package com.kh.clock.order.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResInfoDTO {
  private String accomName;
  private String roomName;
  private String checkInDt;
  private String checkOutDt;
  private int roomPrice;
  private String resCode;
  private String memNo;
}
