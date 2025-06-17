package com.kh.clock.order.repository.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderListDTO {
  private String orderId;
  private String receiptId;
  private List<String> resCode;
}
