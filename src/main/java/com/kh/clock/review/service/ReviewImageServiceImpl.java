package com.kh.clock.review.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.kh.clock.review.repository.dao.ReviewImageDAO;
import com.kh.clock.review.repository.dto.ReviewImageDTO;

@Service
public class ReviewImageServiceImpl implements ReviewImageService{
  private ReviewImageDAO reviewImageDAO;
  
  public ReviewImageServiceImpl(ReviewImageDAO reviewImageDAO) {
    this.reviewImageDAO=reviewImageDAO;
  }
  
  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public int insertReviewImage(ReviewImageDTO reviewimageDTO) {
    return reviewImageDAO.insertReviewImage(reviewimageDTO);
  }

}
