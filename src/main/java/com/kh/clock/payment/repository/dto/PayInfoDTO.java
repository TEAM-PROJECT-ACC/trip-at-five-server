package com.kh.clock.payment.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayInfoDTO {
  private String receiptId;
  private String payMethod;
  private int payPrice;
}
