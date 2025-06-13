package com.kh.clock.review.service;

import org.springframework.web.multipart.MultipartFile;
import com.kh.clock.review.domain.ReviewVO;

public interface ReviewService {
  
  int insertReview(ReviewVO review, MultipartFile[] images);
}
