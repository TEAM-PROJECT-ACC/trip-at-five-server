package com.kh.clock.review.repository.dao;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import com.kh.clock.review.repository.dto.ReviewImageDTO;

@Repository
public class ReviewImageDAO {
private final SqlSession sqlSession;
  
  public ReviewImageDAO(SqlSession sqlsSession) {
    this.sqlSession = sqlsSession;
  }
  
  //후기 이미지 리스트 조회 (reviewMapper의 selectReviewImageListByRevNo 사용)
  public List<ReviewImageDTO> selectReviewImageListByRevNo(int revNo) {
      return sqlSession.selectList("reviewMapper.selectReviewImageListByRevNo", revNo);
  }

  // 후기 이미지 저장 (reviewImageMapper의 insertReviewImage 사용)
  public int insertReviewImage(ReviewImageDTO dto) {
      return sqlSession.insert("reviewImageMapper.insertReviewImage", dto);
  }
}
