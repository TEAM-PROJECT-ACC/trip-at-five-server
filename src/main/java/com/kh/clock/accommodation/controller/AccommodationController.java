package com.kh.clock.accommodation.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kh.clock.accommodation.repository.dto.AccomDTO;
import com.kh.clock.accommodation.repository.dto.AccomListInfoDTO;
import com.kh.clock.accommodation.service.AccomService;

@RestController
@RequestMapping("/accommodations")
public class AccommodationController {
  
  private AccomService accomService;
  
  public AccommodationController(AccomService accomService) {
    this.accomService=accomService;
  }
  // 숙박 목록 조회
  @GetMapping
  public List<AccomDTO> selectAccomList(@ModelAttribute AccomListInfoDTO accomListInfoDTO){
    return accomService.selectAccomList(accomListInfoDTO);
  }
  
  // 숙박 상세 페이지 조회
  @GetMapping("/{accomSq}")
  public ResponseEntity<AccomDTO> getAccommodationById(@PathVariable int accomSq) {
    System.out.println(accomSq);
    AccomDTO accomDetail = accomService.getAccommodationById(accomSq);
    
    if (accomDetail == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(accomDetail);
  }
  
}
