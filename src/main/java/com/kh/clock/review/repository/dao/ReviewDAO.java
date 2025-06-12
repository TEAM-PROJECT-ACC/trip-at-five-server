package com.kh.clock.review.repository.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import com.kh.clock.review.domain.ReviewVO;

@Repository
public class ReviewDAO {
  private final SqlSession sqlSession;
  
  public ReviewDAO(SqlSession sqlSession) {
    this.sqlSession=sqlSession;
  }
  
  // 리뷰 저장
  public int insertReview(ReviewVO review) {
    return sqlSession.insert("reviewMapper.insertReview", review);
  }

}
