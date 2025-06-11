package com.kh.clock.cart.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kh.clock.cart.repository.dto.CartInfoDTO;
import com.kh.clock.cart.service.CartServiceImpl;

@RestController
@RequestMapping("/carts")
public class CartController {
  
  private final CartServiceImpl cartService;
  
  public CartController(CartServiceImpl cartService) {
    this.cartService = cartService;
  }

  @PostMapping("")
  public ResponseEntity<Object> insertCart(@RequestBody List<CartInfoDTO> cartInfo) {
    cartInfo.forEach(cart -> System.out.println(cart));
    
    int insertResult = cartService.insertCart(cartInfo);
    
    return null;
  }
}
