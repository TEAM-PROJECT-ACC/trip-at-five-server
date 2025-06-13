package com.kh.clock.diary.repository;

import java.util.List;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import com.kh.clock.common.pageInfo.PageInfo;
import com.kh.clock.diary.domain.DiaryVO;

@Repository
public class DiaryDAO {
  // sqlSession 생성
  private final SqlSession sqlSession;
  
  public DiaryDAO(SqlSession sqlSession) {
    this.sqlSession = sqlSession;
  }
  
  /**
   * @param int memNo
   * @return int totalCount
   */
  public int selectTotalCount(int memNo) {
    return sqlSession.selectOne("diaryMapper.selectTotalCount", memNo);
  }
  
  /**
   * @param int memberNo
   * @param int pageNo
   * @return List<DiaryVO>
   */
  public List<DiaryVO> selectAllList(int memNo, PageInfo pageInfo) {
	  int offset = (pageInfo.getPageNo() - 1) * pageInfo.getNumOfRows(); 
    RowBounds rowBounds = new RowBounds(offset, pageInfo.getNumOfRows());
    
    return sqlSession.selectList("diaryMapper.selectAllList", memNo, rowBounds);
  }

  /**
   * @param diaryDTO
   * @return DiaryVO
   */
  public DiaryVO selectDiary(DiaryVO diaryDTO) {
    return sqlSession.selectOne("diaryMapper.selectDiary", diaryDTO);
  }

  /**
   * @param modifiedDiary
   * @return int
   */
  public int updateDiary(DiaryVO modifiedDiary) {
    return sqlSession.update("diaryMapper.updateDiary", modifiedDiary);
  }

  /**
   * @param diaryDTO
   * @return int
   */
  public int deleteDiary(DiaryVO diaryDTO) {
    return sqlSession.delete("diaryMapper.deleteDiary", diaryDTO);
  }

  /**
   * @param diary
   * @return int
   */
  public int insertDiary(DiaryVO diary) {
    return sqlSession.insert("diaryMapper.insertDiary", diary);
  }
}
