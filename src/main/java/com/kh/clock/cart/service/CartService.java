package com.kh.clock.cart.service;

import java.util.List;
import com.kh.clock.cart.repository.dto.CartInfoDTO;

public interface CartService {

  int insertCart(List<CartInfoDTO> cartInfo);

}
