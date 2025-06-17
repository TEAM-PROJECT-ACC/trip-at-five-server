package com.kh.clock.payment.service;

import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.kh.clock.order.repository.dao.OrderDAO;
import com.kh.clock.payment.domain.PaymentVO;
import com.kh.clock.payment.repository.dao.PaymentDAO;
import com.kh.clock.payment.repository.dto.CancelDTO;
import com.kh.clock.payment.repository.dto.ConfirmDTO;
import com.kh.clock.reservation.repository.dao.ReservationDAO;
import kr.co.bootpay.pg.Bootpay;
import kr.co.bootpay.pg.model.request.Cancel;

@Service
public class PaymentServiceImpl implements PaymentService {
  @Value("${bootpay.application-id}")
  private String appId;
  
  @Value("${bootpay.rest-key}")
  private String restKey;

  @Value("${bootpay.private-key}")
  private String priKey;
  
  private final PaymentDAO payDAO;
  private final ReservationDAO resDAO;
  private final OrderDAO orderDAO;
  
  public PaymentServiceImpl(PaymentDAO payDAO, ReservationDAO resDAO, OrderDAO orderDAO) {
    this.payDAO = payDAO;
    this.resDAO = resDAO;
    this.orderDAO = orderDAO;
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

  @Override
  public int updatePayState(ConfirmDTO confirmDTO) {
    return payDAO.updatePayState(confirmDTO);
  }

  @Override
  public int payCancel(CancelDTO cancelDTO) {
    int result = 0;
    try {
      Bootpay bootpay = new Bootpay(restKey, priKey);
      HashMap token = bootpay.getAccessToken();
      if(token.get("error_code") != null) { //failed
          return result;
      }
      
      String receiptId = cancelDTO.getReceiptId();
      
      Cancel cancel = new Cancel();
      cancel.receiptId = receiptId;
      cancel.cancelUsername = "관리자";
      cancel.cancelMessage = cancelDTO.getReceiptId() + " 결제 취소";

      HashMap res = bootpay.receiptCancel(cancel);
      if(res.get("error_code") == null) { //success
          System.out.println("receiptCancel success: " + res);
          // 해당 영수증 ID로 주문 테이블에서 예약코드 목록 가져오기
          List<String> resCodeList = orderDAO.findOrderByReceiptId(receiptId);

          result += payDAO.payCancel(receiptId);
          
          for(String resCd : resCodeList) {
            result += resDAO.payStateCancel(resCd);
          }
          
          
      } else {
          System.out.println("receiptCancel false: " + res);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

}
