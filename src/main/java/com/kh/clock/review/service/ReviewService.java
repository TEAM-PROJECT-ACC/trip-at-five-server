package com.kh.clock.review.service;

import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

import com.kh.clock.review.domain.ReviewVO;

public interface ReviewService {
  
  String getResCode(int memNo, int accomSq);
  
  int insertReview(ReviewVO review, MultipartFile[] images);
  
  Map<String, Object> selectReviewList(int accomSq, int page, int size);
}
