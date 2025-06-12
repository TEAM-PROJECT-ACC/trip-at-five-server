package com.kh.clock.payment.repository.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import com.kh.clock.payment.repository.dto.ConfirmDTO;

@Repository
public class PaymentDAO {
  
  private final SqlSession sqlSession;

  public PaymentDAO(SqlSession sqlSession) {
    this.sqlSession = sqlSession;
  }
  
  public int insertPayment(ConfirmDTO confirmDTO) {
    return sqlSession.insert("paymentMapper.insertPayment", confirmDTO);
  }

}
