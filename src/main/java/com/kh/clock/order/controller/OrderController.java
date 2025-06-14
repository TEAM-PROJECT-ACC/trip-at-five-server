package com.kh.clock.order.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kh.clock.order.repository.dto.OrderInfo;
import com.kh.clock.order.repository.dto.OrderListDTO;
import com.kh.clock.order.service.OrderServiceImpl;

@RestController
@RequestMapping("/orders")
public class OrderController {

  private final OrderServiceImpl orderService;
  
  public OrderController(OrderServiceImpl orderService) {
    this.orderService = orderService;
  }
  
  /**
   * 주문 정보 저장
   * @param orderListDTO : 주문 정보
   * @return 영수증 ID
   */
  @PostMapping("")
  public ResponseEntity<Object> insertOrder(@RequestBody OrderListDTO orderListDTO) {
    System.out.println(orderListDTO);
    int result = orderService.insertOrder(orderListDTO);
    
    if(result > 0) return ResponseEntity.status(HttpStatus.OK).body(orderListDTO.getReceiptId());
    else return ResponseEntity.status(HttpStatus.OK).body("주문 저장에 실패했습니다.");
  }
  
  /**
   * 주문 정보 조회 (영수증 페이지)
   * @param receiptId : 영수증 ID
   * @return
   */
  @GetMapping("/{receiptId}")
  public ResponseEntity<Object> getOrderInfo(@PathVariable String receiptId) {
    System.out.println("receiptId : " + receiptId);
    OrderInfo orderInfo = null;
    if(!receiptId.equals("") && receiptId != null) {
      orderInfo = orderService.findOrderByReceiptId(receiptId);
    }

    if(orderInfo != null) {
      // 회원 경험치 증가
      
      
      return ResponseEntity.status(HttpStatus.OK).body(orderInfo);
    }
    else return ResponseEntity.status(HttpStatus.OK).body("영수증 조회에 실패했습니다.");
  }
}
