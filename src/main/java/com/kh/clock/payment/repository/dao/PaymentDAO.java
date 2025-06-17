package com.kh.clock.payment.repository.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import com.kh.clock.payment.domain.PaymentVO;
import com.kh.clock.payment.repository.dto.ConfirmDTO;
import com.kh.clock.payment.repository.dto.PayInfoDTO;

@Repository
public class PaymentDAO {
  
  private final SqlSession sqlSession;

  public PaymentDAO(SqlSession sqlSession) {
    this.sqlSession = sqlSession;
  }
  
  public int insertPayment(PaymentVO paymentVO) {
    return sqlSession.insert("paymentMapper.insertPayment", paymentVO);
  }

  public PayInfoDTO findPaymentByReceiptId(String receiptId) {
    return sqlSession.selectOne("paymentMapper.findPaymentByReceiptId", receiptId);
  }

  public int updatePayState(ConfirmDTO confirmDTO) {
    return sqlSession.insert("paymentMapper.updatePayment", confirmDTO);
  }

  public int payCancel(String receiptId) {
    return sqlSession.update("paymentMapper.payCancel", receiptId);
  }

}
