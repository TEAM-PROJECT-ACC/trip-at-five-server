package com.kh.clock.accommodation.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.kh.clock.accommodation.repository.dto.AccomDTO;
import com.kh.clock.accommodation.repository.dto.AccomListInfoDTO;
import com.kh.clock.accommodation.service.AccomService;
import com.kh.clock.review.service.ReviewImageServiceImpl;
import com.kh.clock.review.service.ReviewService;
import com.kh.clock.review.service.ReviewServiceImpl;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/accommodations")
@RequiredArgsConstructor
public class AccommodationController {
  
  private final AccomService accomService;
  private final ReviewService reviewService;
  
  // 숙박 목록 조회
  @GetMapping
  public List<AccomDTO> selectAccomList(@ModelAttribute AccomListInfoDTO accomListInfoDTO){
    return accomService.selectAccomList(accomListInfoDTO);
  }
  
  // 숙박 상세 페이지 조회
  @GetMapping("/{accomSq}")
  public ResponseEntity<AccomDTO> getAccommodationById(@PathVariable int accomSq, @RequestParam(value = "memNo", required = false) Integer memNo) {
      AccomDTO accomDetail = accomService.getAccommodationById(accomSq, memNo);
      if (accomDetail == null) return ResponseEntity.notFound().build();
      return ResponseEntity.ok(accomDetail);

  }
  
}
