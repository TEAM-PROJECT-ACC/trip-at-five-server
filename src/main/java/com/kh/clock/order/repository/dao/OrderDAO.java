package com.kh.clock.order.repository.dao;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import com.kh.clock.order.domain.OrderVO;

@Repository
public class OrderDAO {
  private final SqlSession sqlSession;
  
  public OrderDAO(SqlSession sqlSession) {
    this.sqlSession = sqlSession;
  }

  public int insertOrder(OrderVO orderVO) {
    return sqlSession.insert("orderMapper.insertOrder", orderVO);
  }

  public List<String> findOrderByReceiptId(String receiptId) {
    return sqlSession.selectList("orderMapper.findOrderByReceiptId", receiptId);
  }
}
