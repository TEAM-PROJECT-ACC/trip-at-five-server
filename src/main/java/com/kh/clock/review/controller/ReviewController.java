package com.kh.clock.review.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.kh.clock.review.domain.ReviewVO;
import com.kh.clock.review.service.ReviewImageServiceImpl;
import com.kh.clock.review.service.ReviewServiceImpl;

@RestController
@RequestMapping("/api/review")
public class ReviewController {
  
  private ReviewServiceImpl reviewSerivce;
  private ReviewImageServiceImpl reviewImageService;
  
  public ReviewController(ReviewServiceImpl reviewSerivce, ReviewImageServiceImpl reviewImageService) {
    this.reviewSerivce=reviewSerivce;
    this.reviewImageService=reviewImageService;
  }
  
  @PostMapping("")
  public ResponseEntity<Object> insertReview(@ModelAttribute ReviewVO reviewVo, @RequestPart(value = "images", required = false) MultipartFile[] images){
    if(images.length > 5) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미지는 최대 5장까지 가능합니다");
    }
    
    int result = reviewSerivce.insertReview(reviewVo, images);
    
    if(result > 0) return ResponseEntity.status(HttpStatus.OK).body(reviewVo);
    else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("리뷰 등록에 실패했습니다.");
  }
}
