package com.kh.clock.review.repository.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import com.kh.clock.review.domain.ReviewVO;
import com.kh.clock.review.repository.dto.ReviewListDTO;

@Repository
public class ReviewDAO {
  private final SqlSession sqlSession;
  
  public ReviewDAO(SqlSession sqlSession) {
    this.sqlSession=sqlSession;
  }
  
  // 예약코드
  public String findResCode(int memNo, int accomSq) {
    Map<String, Object> params = new HashMap<>();
    params.put("memNo", memNo);
    params.put("accomSq", accomSq);
    return sqlSession.selectOne("reviewMapper.findResCode", params);
  }
  
  // 리뷰 저장
  public int insertReview(ReviewVO review) {
    return sqlSession.insert("reviewMapper.insertReview", review);
  }

  // 리뷰 목록 조회
  public List<ReviewListDTO> selectReveiwList(int accomSq){
    return sqlSession.selectList("reviewMapper.selectReviewList", accomSq);
  }
  
}
