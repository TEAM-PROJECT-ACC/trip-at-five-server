package com.kh.clock.review.repository.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import com.kh.clock.review.repository.dto.ReviewImageDTO;

@Repository
public class ReviewImageDAO {
private final SqlSession sqlSession;
  
  public ReviewImageDAO(SqlSession sqlsSession) {
    this.sqlSession = sqlsSession;
  }
  
  // 리뷰 이미지 저장
  public int insertReviewImage(ReviewImageDTO reviewImageDTO) {
    System.out.println("이미지 : " + reviewImageDTO);
    
    return sqlSession.insert("reviewImageMapper.insertReviewImage", reviewImageDTO);
  }
}
