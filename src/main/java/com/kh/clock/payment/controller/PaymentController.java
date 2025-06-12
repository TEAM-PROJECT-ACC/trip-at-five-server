package com.kh.clock.payment.controller;

import java.util.HashMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kh.clock.payment.repository.dto.ConfirmDTO;
import kr.co.bootpay.pg.Bootpay;

@RestController
@RequestMapping("/payments")
public class PaymentController {
  @Value("${bootpay.application-id}")
  private String appId;
  
  @Value("${bootpay.rest-key}")
  private String restKey;

  @Value("${bootpay.private-key}")
  private String priKey;

  /**
   * 결제 승인
   * @param confirmDTO : 영수증 아이디, 주문 아이디
   */
  @PostMapping("/confirm")
  public ResponseEntity<Object> payLogic(@RequestBody ConfirmDTO confirmDTO) {
      
      try {
          System.out.println("restKey : " + restKey);
          System.out.println("priKey : " + priKey);
          Bootpay bootpay = new Bootpay(restKey, priKey);
          HashMap<String, Object> token = bootpay.getAccessToken();
          
          System.out.println("token : " + token); // 토큰 확인하기
          
          if(token.get("error_code") != null) { // failed
              System.out.println("failed");
          }
          String receiptId = confirmDTO.getRecepit();
          HashMap<String, Object> res = bootpay.confirm(receiptId);
          if(res.get("error_code") == null) { // success
              System.out.println("confirm success: " + res);
              // 결제 정보 저장
              
          } else {
              System.out.println("confirm false: " + res);
          }
      } catch (Exception e) {
          e.printStackTrace();
      }
      
      
  }
}
