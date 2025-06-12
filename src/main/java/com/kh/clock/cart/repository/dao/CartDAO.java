package com.kh.clock.cart.repository.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import com.kh.clock.cart.repository.dto.CartInfoDTO;
import com.kh.clock.cart.repository.dto.CartListDTO;

@Repository
public class CartDAO {
  
  private final SqlSession sqlSession;
  
  public CartDAO(SqlSession sqlSession) {
    this.sqlSession = sqlSession;
  }

  public int insertCart(List<CartInfoDTO> cartInfo) {
    int result = 0;
    for (CartInfoDTO cart : cartInfo) {
      System.out.println(cart);
      result += sqlSession.insert("cartMapper.insertCart", cart);
  }
    return result;
  }

  public List<CartInfoDTO> findCartByMemNoAndRoomNo(int memNo, List<Integer> roomNoList) {
    roomNoList.forEach(value -> System.out.println("findCartByMemNoAndRoomNo : " + value));
    
    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("memNo", memNo);
    paramMap.put("roomNoList", roomNoList);
    
    return sqlSession.selectList("cartMapper.findCartByMemNoAndRoomNo", paramMap);
  }

  public int deleteCart(List<CartInfoDTO> existingList) {
    int result = 0;
    
    for(CartInfoDTO cart : existingList) {
      result += sqlSession.delete("cartMapper.deleteCart", cart);
    }
    
    return result;
  }

  public List<CartListDTO> findCartByMemNo(int memNo) {
    return sqlSession.selectList("cartMapper.findCartByMemNo", memNo);
  }

}
