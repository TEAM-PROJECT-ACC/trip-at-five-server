package com.kh.clock.review.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.kh.clock.member.domain.MemberVO;
import com.kh.clock.common.file.OclockFileUtils;
import com.kh.clock.review.domain.ReviewVO;
import com.kh.clock.review.repository.dto.ReviewDTO;
import com.kh.clock.review.service.ReviewImageServiceImpl;
import com.kh.clock.review.service.ReviewServiceImpl;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

  private final ReviewServiceImpl reviewService;
  private final ReviewImageServiceImpl reviewImageService;
  private final OclockFileUtils oFileUtils;
  
  // 후기 등록
  @PostMapping
  public ResponseEntity<Object> insertReview(
          @ModelAttribute ReviewVO reviewVO,
          @RequestParam("accomSq") int accomSq,
          @RequestPart(value = "images", required = false) MultipartFile[] images) {

      // 예약코드+accomSq 조합이 실제 존재하는지 체크
      boolean validReservation = reviewService.existsValidReservation(reviewVO.getResCd(), accomSq);
      if (!validReservation) {
          return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                  .body("예약 내역이 이 숙소에 존재하지 않습니다.");
      }
      // 중복 등록 체크
      if (reviewService.existsReviewByResCd(reviewVO.getResCd())) {
          return ResponseEntity.status(HttpStatus.CONFLICT)
                  .body("이미 해당 예약으로 작성된 후기가 있습니다.");
      }
      int result = reviewService.insertReview(reviewVO, images);
      if (result > 0)
          return ResponseEntity.status(HttpStatus.OK).body(reviewVO);
      else
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("리뷰 등록 실패");
  }

  @GetMapping("/accommodation/{accomNo}")
  public ResponseEntity<Map<String, Object>> getReviewList(@PathVariable int accomNo) {
      List<ReviewDTO> list = reviewService.selectReviewList(accomNo);
      int count = reviewService.selectReviewCount(accomNo);
      Map<String, Object> result = new HashMap<>();
      result.put("list", list);
      result.put("count", count);
      return ResponseEntity.ok(result);
  }

  @GetMapping("/accommodation/{accomNo}/latest")
  public ResponseEntity<ReviewDTO> getLatestReview(@PathVariable int accomNo) {
      return ResponseEntity.ok(reviewService.selectLatestReview(accomNo));
  }
  
  // 숙소 전체 별점 평균
  @GetMapping("/accommodation/{accomNo}/average")
  public ResponseEntity<Double> getReviewAverageScore(@PathVariable int accomNo) {
      double avg = reviewService.selectReviewAverageScore(accomNo);
      return ResponseEntity.ok(avg);
  }
  
  @GetMapping("/accommodation/{accomNo}/count")
  public ResponseEntity<Integer> getReviewCount(@PathVariable int accomNo) {
      int count = reviewService.selectReviewCount(accomNo);
      return ResponseEntity.ok(count);

  }
  
}
