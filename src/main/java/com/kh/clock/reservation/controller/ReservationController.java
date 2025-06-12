package com.kh.clock.reservation.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kh.clock.reservation.repository.dto.ReservationCodeDTO;
import com.kh.clock.reservation.repository.dto.ReservationDTO;
import com.kh.clock.reservation.service.ReservationServiceImpl;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
  
  private ReservationServiceImpl resService;
  
  public ReservationController(ReservationServiceImpl resService) {
    this.resService = resService;
  }
  
  // 예약코드 생성
  @PostMapping("/code")
  public ResponseEntity<Object> createReservationCode(@RequestBody ReservationCodeDTO resCodeDTO) {
    String resCode = resService.createReservationCode(resCodeDTO);
    
    System.out.println("Controller resCode : " + resCode);
    
    if(resCode != null) return ResponseEntity.status(HttpStatus.OK).body(resCode);
    else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예약코드 생성에 실패했습니다.");
  }
  
  // 사용자 예약 저장
  @PostMapping("")
  public ResponseEntity<Object> insertReservation(
      @RequestBody ReservationDTO reservationDTO,
      @RequestBody List<Integer> roomInfo
     ) {
    System.out.println("reservationDTO : " + reservationDTO);
    roomInfo.forEach(value -> System.out.println("roomInfo : " + value));
    
    int insertResult = resService.insertReservation(reservationDTO, roomInfo);

    if(insertResult > 0) return ResponseEntity.status(HttpStatus.OK).body(reservationDTO.getResCode());
    else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예약을 실패했습니다.");
  
  }
}
