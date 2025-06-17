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

  /**
   * 주문정보 저장
   */
  @Override
  public int insertOrder(OrderListDTO orderListDTO) {
    int result = 0;
    
    for(int i = 0; i < orderListDTO.getResCode().size(); i++) {
      result += orderDAO.insertOrder(new OrderVO(orderListDTO.getOrderId(), orderListDTO.getReceiptId(), orderListDTO.getResCode().get(i)));
    }
    
    return result;
  }

  /**
   * 주문 정보 조회 (영수증 페이지)
   */
  @Override
  public OrderInfo findOrderByReceiptId(String receiptId) {
    // 영수증ID로 주문 테이블에서 예약코드 목록을 구한다.
    List<String> resCdList = orderDAO.findOrderByReceiptId(receiptId);
    
    // 예약자 정보를 조회한다.
    ResUserInfoDTO resUser = resDAO.findResUserInfo(resCdList.get(0));
    
    // 구한 예약코드 목록으로 OrderResInfo 타입의 List 를 구한다.
    List<OrderResInfoDTO> orderResList = resDAO.findReservationByResCd(resCdList);

    // 영수증ID로 결제수단과 총 금액을 구한다.
    PayInfoDTO payInfo = paymentDAO.findPaymentByReceiptId(receiptId);
    
    if(resCdList != null && resUser != null && orderResList != null) {
      // 구한 정보를 기반으로 OrderInfo를 완성하여 반환한다.
      return new OrderInfo(resUser, orderResList, payInfo);
    } else return null;
    
  }

  /**
   * 회원 경험치 증가 (TODO: 회원 쪽으로 로직 이동)
   * @param resInfo : 예약된 예약코드 리스트
   * @param payPrice : 결제 금액
   * @return
   */
  @Override
  public int insertMemExp(List<OrderResInfoDTO> resInfo, int payPrice) {
    return orderDAO.insertMemExp(resInfo, payPrice);
  }

}
