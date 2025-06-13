package com.kh.clock.cart.controller;

import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.kh.clock.cart.repository.dto.CartInfoDTO;
import com.kh.clock.cart.repository.dto.CartListDTO;
import com.kh.clock.cart.service.CartServiceImpl;

@RestController
@RequestMapping("/carts")
public class CartController {
  
  private final CartServiceImpl cartService;
  
  public CartController(CartServiceImpl cartService) {
    this.cartService = cartService;
  }
  
  @GetMapping("/{memNo}")
  public ResponseEntity<Object> selectCarts(
      @PathVariable(value="memNo", required=true) int memNo) {
    System.out.println(memNo);
    List<CartListDTO> cartList = cartService.findCartByMemNo(memNo);
    
    cartList.forEach(cart -> System.out.println("장바구니에 상품 추가할 항목 : " + cart));
    
    if(cartList != null) return ResponseEntity.status(HttpStatus.OK).body(cartList);
    else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("조회된 장바구니가 없습니다.");
  }

  @PostMapping("")
  public ResponseEntity<Object> insertCart(@RequestBody List<CartInfoDTO> cartInfoList) {
      if (cartInfoList.isEmpty()) {
          return ResponseEntity.badRequest().body("장바구니 정보가 없습니다.");
      }

      int memNo = cartInfoList.get(0).getMemNo();
      
      List<Integer> roomNoList = cartInfoList.stream()
                                    // CartInfoDTO 의 getRoomNo로 roomNo값 가져오기
                                    .map(CartInfoDTO::getRoomNo)
                                    // List 형태로 변환
                                    .collect(Collectors.toList());

      System.out.println("===roomNoList====");
      roomNoList.forEach(value -> System.out.println(value));
      System.out.println("===roomNoList====\n");

      List<CartInfoDTO> existingList = cartService.findCartByMemNoAndRoomNo(memNo, roomNoList);

      System.out.println("===existingList====");
      existingList.forEach(value -> System.out.println(value));
      System.out.println("===existingList====\n");
      
      Set<String> existingKeys = existingList.stream()
          // map 메서드를 사용해 각 CartInfoDTO 객체의 memNo와 roomNo를 문자열로 변환
          .map(cart -> cart.getMemNo() + "-" + cart.getRoomNo())
          // map 메서드로 얻은 결과를 Set 자료형을 변환하여 중복 제거
          .collect(Collectors.toSet());

      List<CartInfoDTO> filteredList = cartInfoList.stream()
        .filter(cart -> !existingKeys.contains(cart.getMemNo() + "-" + cart.getRoomNo()))
        .collect(Collectors.toList());
      
      

      System.out.println("===filteredList====");
      filteredList.forEach(value -> System.out.println(value));
      System.out.println("===filteredList====\n");

      if (filteredList.isEmpty()) {
        return ResponseEntity.ok("이미 장바구니에 담긴 상품입니다.");
      }

      int result = cartService.insertCart(filteredList);

      return result > 0
        ? ResponseEntity.ok("장바구니에 추가되었습니다.")
        : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("장바구니 등록에 실패했습니다.");
  }

  
  @DeleteMapping("")
  public ResponseEntity<Object> deleteCart(@RequestBody List<Map<String, Object>> requestData) {
    System.out.println("requestData : " + requestData);
    
    List<CartInfoDTO> deleteList = new ArrayList<>();
    
    requestData.forEach(value -> {
      deleteList.add(new CartInfoDTO((int)value.get("memNo"), (int)value.get("roomNo")));
    });
    
    deleteList.forEach(value -> System.out.println("deleteList : " + value));
    
    int deleteResult = cartService.deleteCart(deleteList);
    
    return deleteResult > 0
        ? ResponseEntity.ok("장바구니에 삭제되었습니다.")
        : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("장바구니 삭제에 실패했습니다.");
  }
}
