package com.kh.clock.order.service;

import org.springframework.stereotype.Service;
import com.kh.clock.order.domain.OrderVO;
import com.kh.clock.order.repository.dao.OrderDAO;
import com.kh.clock.order.repository.dto.OrderListDTO;

@Service
public class OrderServiceImpl implements OrderService {
  
  private final OrderDAO orderDAO;
  
  public OrderServiceImpl(OrderDAO orderDAO) {
    this.orderDAO = orderDAO;
  }

  @Override
  public int insertOrder(OrderListDTO orderListDTO) {
    int result = 0;
    
    for(int i = 0; i < orderListDTO.getResCode().size(); i++) {
      result += orderDAO.insertOrder(new OrderVO(orderListDTO.getOrderId(), orderListDTO.getReceiptId(), orderListDTO.getResCode().get(i)));
    }
    
    return result;
  }

}
