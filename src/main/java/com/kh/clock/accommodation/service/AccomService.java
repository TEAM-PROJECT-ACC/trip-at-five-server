package com.kh.clock.accommodation.service;

import java.util.List;
import com.kh.clock.accommodation.repository.AccomDTO;
import com.kh.clock.accommodation.repository.AccomListInfoDTO;

public interface AccomService {
  
  // 숙박 목록 조회(키워드로 목록 조회)
  List<AccomDTO> selectAccomList(AccomListInfoDTO accomListInfoDTO);
  
  //숙박 상세 페이지 조회
  AccomDTO getAccommodationById(int accomSq);
  
  int createAccom(AccomDTO accom);
  
  int updateAccom(AccomDTO accom);
  
  int deleteAccom(int accomSq);

}
