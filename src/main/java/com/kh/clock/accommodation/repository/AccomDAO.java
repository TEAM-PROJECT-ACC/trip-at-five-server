package com.kh.clock.accommodation.repository;

import java.util.List;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class AccomDAO {
  
  private final SqlSession sqlSession;
  
  public AccomDAO(SqlSession sqlSession) {
    this.sqlSession=sqlSession;
  }
  
  // 숙박 목록 조회(키워드로)
  public List<AccomDTO> selectAccomList(AccomListInfoDTO accomListInfoDTO) {
    int offset = accomListInfoDTO.getPage() * accomListInfoDTO.getSize();
    RowBounds rb = new RowBounds(offset, accomListInfoDTO.getSize());
    
    return sqlSession.selectList("accommodationMapper.selectAccomList", accomListInfoDTO, rb);
  }
 
  // 숙박 상세 조회
  public AccomDTO selectAccomDetail(int accomSq) {
      return sqlSession.selectOne("accommodationMapper.selectAccomDetail", accomSq);
  }
  
  // 숙박 등록
  public int insertAccom(AccomDTO accom) {
    return sqlSession.insert("accommodationMapper.insertAccom", accom);
  }
	  
  // 숙박 수정
  public int updateAccom(AccomDTO accom) {
    return sqlSession.update("accommodationMapper.updateAccom", accom);
  }
	  
  // 숙박 삭제
  public int deleteAccom(int accomSq) {
    return sqlSession.delete("accommodationMapper.deleteAccom", accomSq);
  }
  
  // 숙박 검색으로 숙박 목록 조회
  public List<AccomDTO> searchAccom(AccomListInfoDTO searchFilter) {
    return sqlSession.selectList("accommodationMapper.searchAccom", searchFilter);
  }

  public List<AccomDTO> filterAccomList() {
    return sqlSession.selectList("accommodationMapper.filterAccomList");
  }
  
}
