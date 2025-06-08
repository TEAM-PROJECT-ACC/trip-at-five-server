package com.kh.clock.accommodation.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.kh.clock.accommodation.repository.AccomDAO;
import com.kh.clock.accommodation.repository.dto.AccomAdminDetailDTO;
import com.kh.clock.accommodation.repository.dto.AccomAdminListDTO;
import com.kh.clock.accommodation.repository.dto.AccomAdminSearchDTO;
import com.kh.clock.accommodation.repository.dto.AccomDTO;
import com.kh.clock.accommodation.repository.dto.AccomListInfoDTO;
import com.kh.clock.room.domain.RoomVO;

@Service
public class AccomServiceImpl implements AccomService {
  
  private final AccomDAO accomDAO;

  public AccomServiceImpl(AccomDAO accomDAO) {
      this.accomDAO = accomDAO;
  }

  // 숙박 목록 조회(키워드/AccomListInfoDTO)
  @Override
  public List<AccomDTO> selectAccomList(AccomListInfoDTO accomListInfoDTO) {
      return accomDAO.selectAccomList(accomListInfoDTO);
  }
  
  // 숙박 상세 페이지 조회
  @Override
  public AccomDTO getAccommodationById(int accomSq) {
    // 1. 숙박 업체 정보 조회
      AccomDTO accomDetail = accomDAO.selectAccomDetail(accomSq);
      
    // 2. 해당 숙박 업체의 객실 목록 조회
      List<RoomVO> accomRoom = accomDAO.selectRoomList(accomSq);
      accomDetail.setRoomList(accomRoom);
      
      return accomDetail;
  }

  // 관리자 숙박 목록 조회
  @Override
  public List<AccomAdminListDTO> selectAdminAccomList(AccomAdminSearchDTO accomSearchDTO) {
    return accomDAO.selectAdminAccomList(accomSearchDTO);
  }

  // 관리자 숙박 상세 조회(숙박업체 번호로 조회)
  @Override
  public AccomAdminDetailDTO selectAdminAccomDetailByAccomSq(int accomSq) {
    return accomDAO.selectAdminAccomDetailByAccomSq(accomSq);
  }

  // 관리자 숙박 상세 수정
  @Override
  public int updateAdminAccomDetail(AccomAdminDetailDTO updatedAccomInfo) {
    return accomDAO.updateAdminAccomDetail(updatedAccomInfo);
  }

  // 관리자 숙박 삭제
  @Override
  public int deleteAdminAccom(int accomSq) {
    accomDAO.deleteAdminRoom(accomSq);
    return accomDAO.deleteAdminAccom(accomSq);
  }

  // 관리자 숙박 정보 등록
  @Override
  public int insertAdminAccom(AccomAdminDetailDTO accomDto) {
    return accomDAO.insertAdminAccom(accomDto);
  }
  
}
