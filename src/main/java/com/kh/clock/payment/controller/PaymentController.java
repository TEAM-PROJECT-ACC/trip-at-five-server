package com.kh.clock.payment.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kh.clock.payment.repository.dto.ConfirmDTO;
import com.kh.clock.payment.repository.dto.PayInfoDTO;
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
     PayInfoDTO payInfoDTO = payService.payConfirm(confirmDTO);
    return null;
  }
}
