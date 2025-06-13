package com.kh.clock.payment.domain;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentVO {
  private String receiptId;
  private String orderId;
  private int payPrice;
  private String payMethod;
  private int payStCk;
  private Date payDt;
  private String payReqDt;
  private Date payStDt;
  

  public PaymentVO(String receiptId, String orderId, int payPrice, String payMethod, int payStCk,
      String payReqDt) {
    this.receiptId = receiptId;
    this.orderId = orderId;
    this.payPrice = payPrice;
    this.payMethod = payMethod;
    this.payStCk = payStCk;
    this.payReqDt = payReqDt;
  }
}
