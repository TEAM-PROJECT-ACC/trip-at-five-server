package com.kh.clock.reservation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kh.clock.reservation.repository.dto.ReservationCodeDTO;
import com.kh.clock.reservation.service.ReservationServiceImpl;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
  
  private ReservationServiceImpl resService;
  
  public ReservationController(ReservationServiceImpl resService) {
    this.resService = resService;
  }
  
  @GetMapping("code")
  public ResponseEntity<Object> createReservationCode(ReservationCodeDTO resCodeDTO) {
    String resCode = resService.createReservationCode(resCodeDTO);
    
    System.out.println("Controller resCode : " + resCode);
    
    if(resCode != null) return ResponseEntity.status(HttpStatus.OK).body(resCode);
    else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예약코드 생성에 실패했습니다.");
  }
}
