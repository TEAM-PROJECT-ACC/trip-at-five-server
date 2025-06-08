package com.kh.clock.accommodation.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
  
  // 숙박 업체 정보 상세 페이지(숙박업체 번호)
  @GetMapping("/{accomSq}/edit")
  public ResponseEntity<AccomAdminDetailDTO> selectAdminAccomDetailByAccomSq(@PathVariable int accomSq){
    AccomAdminDetailDTO accomDetail = accomService.selectAdminAccomDetailByAccomSq(accomSq);
    if (accomDetail == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(accomDetail);
  }
  
  // 숙박 업체 정보 상세 페이지 (수정)
  @PutMapping("/{accomSq}/edit")
  public ResponseEntity<String> updateAccommodation(@RequestBody AccomAdminDetailDTO updatedAccomInfo) {
    int result = accomService.updateAdminAccomDetail(updatedAccomInfo);
    if (result > 0) {
      return ResponseEntity.ok("수정 성공");
    } else {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("수정 실패");
    }
  }
  
  // 숙박 업체 정보 상세 페이지 (삭제)
  @DeleteMapping("/{accomSq}")
  public ResponseEntity<String> deleteAdminAccom(@PathVariable int accomSq) {
    int result = accomService.deleteAdminAccom(accomSq);
    if (result > 0) {
      return ResponseEntity.ok("삭제 완료");
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("삭제할 숙박 정보가 없습니다.");
    }
  }
  
  // 숙박 업체 정보 등록
  @PostMapping("/new")
  public ResponseEntity<String> registerAccommodation(@RequestBody AccomAdminDetailDTO accomDto) {
    int result = accomService.insertAdminAccom(accomDto);
    if (result > 0) {
      return ResponseEntity.ok("등록 완료");
    } else {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("등록 실패");
    }
  }
}
