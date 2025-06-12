package com.kh.clock.payment.service;

import java.util.HashMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.kh.clock.payment.domain.PaymentVO;
import com.kh.clock.payment.repository.dao.PaymentDAO;
import com.kh.clock.payment.repository.dto.ConfirmDTO;
import kr.co.bootpay.pg.Bootpay;

@Service
public class PaymentServiceImpl implements PaymentService {
  @Value("${bootpay.application-id}")
  private String appId;
  
  @Value("${bootpay.rest-key}")
  private String restKey;

  @Value("${bootpay.private-key}")
  private String priKey;
  
  private final PaymentDAO payDAO;
  
  public PaymentServiceImpl(PaymentDAO payDAO) {
    this.payDAO = payDAO;
  }

  @Override
  public int payConfirm(ConfirmDTO confirmDTO) {
    int payResult = 0;
    try {
      System.out.println("restKey : " + restKey);
      System.out.println("priKey : " + priKey);
      Bootpay bootpay = new Bootpay(restKey, priKey);
      HashMap<String, Object> token = bootpay.getAccessToken();
      
      System.out.println("token : " + token); // 토큰 확인하기
      
      if(token.get("error_code") != null) { // failed
          System.out.println("failed");
      }
      String receiptId = confirmDTO.getReceiptId();
      HashMap<String, Object> res = bootpay.confirm(receiptId);
      if(res.get("error_code") == null) { // success
          System.out.println("confirm success: " + res);
          payResult = payDAO.insertPayment(
              new PaymentVO(
                  (String)res.get("receipt_id"),
                  (String)res.get("order_id"),
                  (int)res.get("price"), 
                  (String)res.get("method"), 
                  (int)res.get("status"),
                  res.get("requested_at").toString().substring(0, 10)
              )
          );
      } else {
          System.out.println("confirm false: " + res);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    return payResult;
  }

}
