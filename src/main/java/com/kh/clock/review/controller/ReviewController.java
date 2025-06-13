package com.kh.clock.review.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.kh.clock.member.domain.MemberVO;
import com.kh.clock.review.domain.ReviewVO;
import com.kh.clock.review.service.ReviewImageServiceImpl;
import com.kh.clock.review.service.ReviewServiceImpl;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
  
  private final ReviewServiceImpl reviewSerivce;
  private final ReviewImageServiceImpl reviewImageService;
  
  // 리뷰 등록
  @PostMapping
  public ResponseEntity<Object> insertReview(@ModelAttribute ReviewVO reviewVo, @RequestPart(value = "images", required = false) MultipartFile[] images, HttpSession session){
    
    MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
    if (loginUser == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 필요함");
    }
    
    if(images != null && images.length > 5) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미지는 최대 5장까지 가능합니다");
    }
    
    int memNo = loginUser.getMemSq();
    int accomSq = reviewVo.getAccomSq(); 
    
    String resCd = reviewSerivce.getResCode(memNo, reviewVo.getAccomSq());
    
    if (resCd == null) {
      return ResponseEntity.status(403).body("숙소의 예약 이력 없음");
    }
    
    reviewVo.setResCd(resCd);
    
    // 등록
    int result = reviewSerivce.insertReview(reviewVo, images);
    if(result > 0) {
      return ResponseEntity.status(HttpStatus.OK).body(reviewVo); 
    } else {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("리뷰 등록에 실패했습니다.");
    }
  }
  
}
