package com.kh.clock.order.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderVO {
  private String orderId;
  private String receiptId;
  private String resCode;
}
