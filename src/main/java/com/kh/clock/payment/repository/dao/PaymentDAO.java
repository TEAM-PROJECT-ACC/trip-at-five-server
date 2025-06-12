package com.kh.clock.payment.repository.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import com.kh.clock.payment.domain.PaymentVO;

@Repository
public class PaymentDAO {
  
  private final SqlSession sqlSession;

  public PaymentDAO(SqlSession sqlSession) {
    this.sqlSession = sqlSession;
  }
  
  public int insertPayment(PaymentVO paymentVO) {
    return sqlSession.insert("paymentMapper.insertPayment", paymentVO);
  }

}
