package com.kh.clock.review.repository.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import com.kh.clock.review.domain.ReviewVO;
import com.kh.clock.review.repository.dto.ReviewListDTO;
import com.kh.clock.review.repository.dto.ReviewDTO;

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
  
  public List<ReviewDTO> selectReviewList(int accomSq) {
    return sqlSession.selectList("reviewMapper.selectReviewList", accomSq);
  }

  public ReviewDTO selectLatestReview(int accomNo) {
    return sqlSession.selectOne("reviewMapper.selectLatestReview", accomNo);
  }

  public int countReviewByResCd(String resCd) {
    return sqlSession.selectOne("reviewMapper.countReviewByResCd", resCd);
  }

  public int countValidReservation(Map<String, Object> param) {
    return sqlSession.selectOne("reviewMapper.countValidReservation", param);
  }

  public int selectReviewCount(int accomNo) {
    return sqlSession.selectOne("reviewMapper.selectReviewCount", accomNo);
  }

  public double selectReviewAverageScore(int accomNo) {
    Double result = sqlSession.selectOne("reviewMapper.selectReviewAverageScore", accomNo);
    return result != null ? result : 0.0;
  }

}
