package com.kh.clock.cart.service;

import java.util.List;
import com.kh.clock.cart.repository.dto.CartInfoDTO;

public interface CartService {

  int insertCart(List<CartInfoDTO> cartInfo);

  List<CartInfoDTO> findCartByMemNoAndRoomNo(int memNo, List<Integer> cartInfo);

  int deleteCart(List<CartInfoDTO> existingList);

}
