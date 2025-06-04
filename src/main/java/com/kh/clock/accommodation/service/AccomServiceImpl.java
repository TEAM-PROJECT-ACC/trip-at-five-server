package com.kh.clock.accommodation.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.kh.clock.accommodation.repository.AccomDAO;
import com.kh.clock.accommodation.repository.AccomDTO;
import com.kh.clock.accommodation.repository.AccomListInfoDTO;

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
    
    // 2. 해당 숙박 업체의 객실 목록 조회
      return accomDAO.selectAccomDetail(accomSq);
  }

  // 숙박 등록
  @Override
  public int createAccom(AccomDTO accom) {
      return accomDAO.insertAccom(accom);
  }

  // 숙박 정보 수정
  @Override
  public int updateAccom(AccomDTO accom) {
      return accomDAO.updateAccom(accom);
  }

  // 숙박 정보 삭제
  @Override
  public int deleteAccom(int accomSq) {
      return accomDAO.deleteAccom(accomSq);
  }

}
