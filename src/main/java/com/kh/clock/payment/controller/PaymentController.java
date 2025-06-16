package com.kh.clock.payment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kh.clock.payment.repository.dto.CancelDTO;
import com.kh.clock.payment.repository.dto.ConfirmDTO;
import com.kh.clock.payment.service.PaymentServiceImpl;

@RestController
@RequestMapping("/payments")
public class PaymentController {
  
  private final PaymentServiceImpl payService;
  
  public PaymentController(PaymentServiceImpl payService) {
    this.payService = payService;
  }

  /**
   * 결제 승인
   * @param confirmDTO : 영수증 아이디, 주문 아이디
   */
  @PostMapping("/confirm")
  public ResponseEntity<Object> payConfirm(@RequestBody ConfirmDTO confirmDTO) {
    System.out.println(confirmDTO);
    int insertPayResult = payService.payConfirm(confirmDTO);
    
    if(insertPayResult > 0) return ResponseEntity.status(HttpStatus.OK).body(confirmDTO);
    else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("결제 승인을 실패했습니다.");
  }
  
  /**
   * 결제 취소
   * @param requestBody
   * @return
   */
  @PostMapping("/cancel")
  public ResponseEntity<Object> payCancel(@RequestBody CancelDTO cancelDTO) {
    System.out.println(cancelDTO);
    
    int cancelResult = payService.payCancel(cancelDTO);

    if(cancelResult > 0) return ResponseEntity.status(HttpStatus.OK).body(cancelDTO);
    else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("결제 승인을 실패했습니다.");
  }
}
