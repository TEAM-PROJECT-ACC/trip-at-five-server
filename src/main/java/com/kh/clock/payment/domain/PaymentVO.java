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
  private int payPrice;
  private String payMethod;
  private int payStDt;
  private Date payReqDt;
  private String resCd;
}
