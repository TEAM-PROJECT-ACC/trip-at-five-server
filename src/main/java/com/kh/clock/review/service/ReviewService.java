package com.kh.clock.review.service;

import java.util.Map;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.kh.clock.review.domain.ReviewVO;
import com.kh.clock.review.repository.dto.ReviewDTO;

public interface ReviewService {
  
  String findResCode(int memNo, int accomSq);
  
  int insertReview(ReviewVO review, MultipartFile[] images);

  
  Map<String, Object> selectReviewList(int accomSq, int page, int size);

  List<ReviewDTO> selectReviewList(int accomSq);
  
  ReviewDTO selectLatestReview(int accomNo);
  
  boolean existsReviewByResCd(String resCd);
  
  boolean existsValidReservation(String resCd, int accomSq);
  
  int selectReviewCount(int accomNo);
  
  double selectReviewAverageScore(int accomNo);

}
