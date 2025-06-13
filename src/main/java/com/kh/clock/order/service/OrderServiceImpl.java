package com.kh.clock.order.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.kh.clock.order.domain.OrderVO;
import com.kh.clock.order.repository.dao.OrderDAO;
import com.kh.clock.order.repository.dto.OrderInfo;
import com.kh.clock.order.repository.dto.OrderListDTO;
import com.kh.clock.order.repository.dto.OrderResInfoDTO;
import com.kh.clock.payment.repository.dao.PaymentDAO;
import com.kh.clock.payment.repository.dto.PayInfoDTO;
import com.kh.clock.reservation.repository.dao.ReservationDAO;
import com.kh.clock.reservation.repository.dto.ResUserInfoDTO;

@Service
public class OrderServiceImpl implements OrderService {
  
  private final OrderDAO orderDAO;
  private final ReservationDAO resDAO;
  private final PaymentDAO paymentDAO;
  
  public OrderServiceImpl(OrderDAO orderDAO, ReservationDAO resDAO, PaymentDAO paymentDAO) {
    this.orderDAO = orderDAO;
    this.resDAO = resDAO;
    this.paymentDAO = paymentDAO;
  }

  @Override
  public int insertOrder(OrderListDTO orderListDTO) {
    int result = 0;
    
    for(int i = 0; i < orderListDTO.getResCode().size(); i++) {
      result += orderDAO.insertOrder(new OrderVO(orderListDTO.getOrderId(), orderListDTO.getReceiptId(), orderListDTO.getResCode().get(i)));
    }
    
    return result;
  }

  @Override
  public OrderInfo findOrderByReceiptId(String receiptId) {
    // 영수증ID로 주문 테이블에서 예약코드 목록을 구한다.
    List<String> resCodeList = orderDAO.findOrderByReceiptId(receiptId);
    
    // 예약자 정보를 조회한다.
    ResUserInfoDTO resUser = resDAO.findResUserInfo(resCodeList.get(0));
    
    // 구한 예약코드 목록으로 OrderResInfo 타입의 List 를 구한다.
    List<OrderResInfoDTO> orderResList = resDAO.findReservationByResCd(resCodeList);

    // 영수증ID로 결제수단과 총 금액을 구한다.
    PayInfoDTO payInfo = paymentDAO.findPaymentByReceiptId(receiptId);
    
    if(resCodeList != null && resUser != null && orderResList != null) {
      // 구한 정보를 기반으로 OrderInfo를 완성하여 반환한다.
      return new OrderInfo(resUser, orderResList, payInfo);
    } else return null;
    
  }

}
