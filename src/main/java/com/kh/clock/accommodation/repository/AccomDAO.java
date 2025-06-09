package com.kh.clock.accommodation.repository;

import java.util.List;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import com.kh.clock.accommodation.repository.dto.AccomAdminDetailDTO;
import com.kh.clock.accommodation.repository.dto.AccomAdminListDTO;
import com.kh.clock.accommodation.repository.dto.AccomAdminSearchDTO;
import com.kh.clock.accommodation.repository.dto.AccomDTO;
import com.kh.clock.accommodation.repository.dto.AccomListInfoDTO;
import com.kh.clock.room.domain.RoomVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class AccomDAO {
  
  private final SqlSession sqlSession;
  
  
  // 숙박 목록 조회(키워드로)
  public List<AccomDTO> selectAccomList(AccomListInfoDTO accomListInfoDTO) {
    int offset = accomListInfoDTO.getPage() * accomListInfoDTO.getSize();
    RowBounds rb = new RowBounds(offset, accomListInfoDTO.getSize());
    
    log.info("{}", accomListInfoDTO);
    log.info("{}", offset);
   
    return sqlSession.selectList("accommodationMapper.selectAccomList", accomListInfoDTO, rb);
  }
 
  // 숙박 상세 조회
  public AccomDTO selectAccomDetail(int accomSq) {
    log.info("DAO: accomSq = {}", accomSq);
    AccomDTO accom = sqlSession.selectOne("accommodationMapper.selectAccomDetail", accomSq);
    log.info("DAO: accomDetail = {}", accom);
    return accom;
  }
  
  /**
   * 숙박 상세 객실 정보 조회
   * @param accomNo 숙박 업체 번호
   * @return 해당 숙박 업체의 객실 목록
   */
  
  public List<RoomVO> selectRoomList(int accomNo) {
    return sqlSession.selectList("accommodationMapper.selectRoomList", accomNo);
  }
  
  // 숙박 검색으로 숙박 목록 조회
  public List<AccomDTO> searchAccom(AccomListInfoDTO searchFilter) {
    return sqlSession.selectList("accommodationMapper.searchAccom", searchFilter);
  }

  // 관리자 숙박 목록 조회
  public List<AccomAdminListDTO> selectAdminAccomList(AccomAdminSearchDTO accomSearchDTO) {
    return sqlSession.selectList("accommodationMapper.selectAdminAccomList", accomSearchDTO);
  }
  
  // 관리자 숙박 상세 조회
  public AccomAdminDetailDTO selectAdminAccomDetailByAccomSq(int accomSq) {
    return sqlSession.selectOne("accommodationMapper.selectAdminAccomDetail", accomSq);
  }

  // 관리자 숙박 상세 수정
  public int updateAdminAccomDetail(AccomAdminDetailDTO updatedAccomInfo) {
    return sqlSession.update("accommodationMapper.updateAdminAccomDetail", updatedAccomInfo);
  }

  // 관리자 숙박 삭제
  public int deleteAdminAccom(int accomSq) {
    return sqlSession.delete("accommodationMapper.deleteAdminAccom", accomSq);
  }
  
  // 관리자 숙박 삭제를 위한 객실 정보 삭제 우선
  public int deleteAdminRoom(int accomSq) {
    return sqlSession.delete("accommodationMapper.deleteAdminRoom", accomSq);
  }

  // 관리자 숙박 등록
  public int insertAdminAccom(AccomAdminDetailDTO accomDto) {
    return sqlSession.insert("accommodationMapper.insertAdminAccom", accomDto);
  }

}
