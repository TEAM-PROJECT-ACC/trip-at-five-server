package com.kh.clock.order.repository.dao;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import com.kh.clock.member.repository.MemExpDTO;
import com.kh.clock.order.domain.OrderVO;
import com.kh.clock.order.repository.dto.OrderResInfoDTO;

@Repository
public class OrderDAO {

  // 가중치 값과 퍼센트 값으로 추후 따로 분리해서 관리하는것이 좋을까..? 의논해볼 주제
  final static int ALPHA = 2;
  final static int BETA = 1;
  final static int PERCENT = 10;
  
  private final SqlSession sqlSession;
  
  public OrderDAO(SqlSession sqlSession) {
    this.sqlSession = sqlSession;
  }

  public int insertOrder(OrderVO orderVO) {
    System.out.println("orderVO : " + orderVO);
    return sqlSession.insert("orderMapper.insertOrder", orderVO);
  }

  public List<String> findOrderByReceiptId(String receiptId) {
    return sqlSession.selectList("orderMapper.findOrderByReceiptId", receiptId);
  }

  public int insertMemExp(List<OrderResInfoDTO> resInfo, int payPrice) {
    resInfo.forEach(value -> System.out.println("value : " + value));
    
    int result = 0;
    
    for(int i = 0; i < resInfo.size(); i++) {
      int memExp = calcMemExp(resInfo.get(i).getRoomPrice(), payPrice);
      
      result += sqlSession.insert("orderMapper.insertMemExp", new MemExpDTO(resInfo.get(i).getMemNo(), resInfo.get(i).getResCd(), memExp));
      // TODO: 회원 경험치 내역 로그 출력
    }
    
    return result;
  }

  /**
   * 경험치 로직
   * @param roomPrice : 예약 객실 가격
   * @param payPrice : 전체 금액
   * @return
   */
  private int calcMemExp(int roomPrice, int payPrice) {
    return (((roomPrice / 1000) * ALPHA) + ((payPrice / 10000) * BETA)) / PERCENT;
  }
}
