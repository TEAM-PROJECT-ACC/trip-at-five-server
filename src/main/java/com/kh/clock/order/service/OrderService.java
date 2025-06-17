package com.kh.clock.order.service;

import java.util.List;
import com.kh.clock.order.repository.dto.OrderInfo;
import com.kh.clock.order.repository.dto.OrderListDTO;
import com.kh.clock.order.repository.dto.OrderResInfoDTO;

public interface OrderService {
  /**
   * 주문정보 저장
   */
  int insertOrder(OrderListDTO orderListDTO);

  /**
   * 주문 정보 조회 (영수증 페이지)
   */
  OrderInfo findOrderByReceiptId(String receiptId);

  /**
   * 회원 경험치 증가 (TODO: 회원 쪽으로 로직 이동)
   * @param resInfo : 예약된 예약코드 리스트
   * @param payPrice : 결제 금액
   * @return
   */
  int insertMemExp(List<OrderResInfoDTO> resInfo, int payPrice);
}
