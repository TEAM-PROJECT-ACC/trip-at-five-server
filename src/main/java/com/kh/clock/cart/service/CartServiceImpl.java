package com.kh.clock.cart.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.kh.clock.cart.repository.dao.CartDAO;
import com.kh.clock.cart.repository.dto.CartInfoDTO;

@Service
public class CartServiceImpl implements CartService {
  
  private final CartDAO cartDAO;
  
  public CartServiceImpl(CartDAO cartDAO) {
    this.cartDAO = cartDAO;
  }

  @Override
  public int insertCart(List<CartInfoDTO> cartInfo) {
    return cartDAO.insertCart(cartInfo);
  }

  @Override
  public List<CartInfoDTO> findCartByMemNoAndRoomNo(int memNo, List<Integer> cartInfo) {
    return cartDAO.findCartByMemNoAndRoomNo(memNo, cartInfo);
  }

  @Override
  public int deleteCart(List<CartInfoDTO> existingList) {
    return cartDAO.deleteCart(existingList);
  }

}
