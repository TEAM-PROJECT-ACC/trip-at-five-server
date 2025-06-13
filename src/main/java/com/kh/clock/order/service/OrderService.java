package com.kh.clock.order.service;

import com.kh.clock.order.repository.dto.OrderInfo;
import com.kh.clock.order.repository.dto.OrderListDTO;

public interface OrderService {

  int insertOrder(OrderListDTO orderListDTO);

  OrderInfo findOrderByReceiptId(String receiptId);

}
