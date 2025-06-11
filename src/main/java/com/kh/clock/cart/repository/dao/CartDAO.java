package com.kh.clock.cart.repository.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import com.kh.clock.cart.repository.dto.CartInfoDTO;

@Repository
public class CartDAO {
  
  private final SqlSession sqlSession;
  
  public CartDAO(SqlSession sqlSession) {
    this.sqlSession = sqlSession;
  }

  public int insertCart(@Param("list") List<CartInfoDTO> cartInfo) {
    int result = 0;
    for (CartInfoDTO cart : cartInfo) {
      result += sqlSession.insert("cartMapper.insertCart", cart);
  }
    return result;
  }

}
