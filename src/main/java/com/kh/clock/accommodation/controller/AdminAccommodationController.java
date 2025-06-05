package com.kh.clock.accommodation.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kh.clock.accommodation.repository.dto.AccomAdminDetailDTO;
import com.kh.clock.accommodation.repository.dto.AccomAdminListDTO;
import com.kh.clock.accommodation.repository.dto.AccomAdminSearchDTO;

import com.kh.clock.accommodation.service.AccomService;
// 관리자 페이지 기능
@RestController
@RequestMapping("/admin/accommodations")
public class AdminAccommodationController {
  
  private AccomService accomService;
  public AdminAccommodationController(AccomService accomService) {
    this.accomService=accomService;
  }

  //숙박 업체 정보 목록 조회
  @GetMapping
  public List<AccomAdminListDTO> selectAdminAccomList(@ModelAttribute AccomAdminSearchDTO accomSearchDTO) {
    return accomService.selectAdminAccomList(accomSearchDTO);
  }
  
  @GetMapping("/{accomSq}/edit")
  public ResponseEntity<AccomAdminDetailDTO> selectAdminAccomDetailByAccomSq(@PathVariable int accomSq){
    AccomAdminDetailDTO accomDetail = accomService.selectAdminAccomDetailByAccomSq(accomSq);
    if (accomDetail == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(accomDetail);
  }
 
}
