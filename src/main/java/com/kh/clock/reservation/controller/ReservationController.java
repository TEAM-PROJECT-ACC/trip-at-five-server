package com.kh.clock.reservation.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kh.clock.reservation.repository.dto.ResCodeListDTO;
import com.kh.clock.reservation.repository.dto.ReservationDTO;
import com.kh.clock.reservation.service.ReservationServiceImpl;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
  
  private ReservationServiceImpl resService;
  
  public ReservationController(ReservationServiceImpl resService) {
    this.resService = resService;
  }
  
  // 주문ID 생성
  @PostMapping("/order-id")
  public ResponseEntity<Object> createOrderId(@RequestBody ResCodeListDTO resCodeListDTO) {
    System.out.println(resCodeListDTO);
    
    String orderId = resService.createOrderId(resCodeListDTO);
    
    System.out.println("Controller resCode : " + orderId);
    
    if(orderId != null) return ResponseEntity.status(HttpStatus.OK).body(orderId);
    else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("주문ID 생성에 실패했습니다.");
  }
  
  // 사용자 예약 정보 저장
  @PostMapping("")
  public ResponseEntity<Object> insertReservation(
      @RequestBody ReservationDTO reservationDTO
     ) {
    System.out.println("reservationDTO : " + reservationDTO);
    
    reservationDTO.getRoomInfo().forEach(value -> System.out.println("roomInfo : " + value));
    
    // 예약 저장 후 예약코드 리스트 반환
    List<String> resCodeList = resService.insertReservation(reservationDTO, reservationDTO.getRoomInfo());

    if(resCodeList != null) return ResponseEntity.status(HttpStatus.OK).body(resCodeList);
    else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예약을 실패했습니다.");
  
  }
}
