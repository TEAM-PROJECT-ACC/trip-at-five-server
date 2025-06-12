package com.kh.clock.payment.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmDTO {
  private String receiptId; // 영수증아이디
  private String orderId; // 주문아이디
}
