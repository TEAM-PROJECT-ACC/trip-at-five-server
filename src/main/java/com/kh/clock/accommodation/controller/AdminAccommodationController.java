package com.kh.clock.accommodation.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.kh.clock.accommodation.repository.AccomDTO;
// 관리자 페이지 기능
@RestController
@RequestMapping("/admin/accommodations")
public class AdminAccommodationController {
  // accomSq, accom_type_no => accom_type_name(불러옴), accom_name, accom_addr, accom_phone, min(room_price)
  //숙박 업체 정보 목록 조회
  @GetMapping
  public List selectAccom() {
    return null;
  }
 
  // 숙박 업체 정보 등록
  @PostMapping
  public int createAccom(@RequestBody AccomDTO accom) {
    return 0;
  }
  
  // 숙박 업체 정보 수정
  @PutMapping
  public int updateAccom(@RequestBody AccomDTO accom) {
    return 0;
  }
  
  //숙박 업체 등록 삭제
  @DeleteMapping
  public int deleteAccom(@RequestParam int accomSq) {
    return 0;
  }
}
