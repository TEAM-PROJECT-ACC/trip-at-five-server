package com.kh.clock.accommodation.service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.kh.clock.accommodation.repository.dto.AccomAdminDetailDTO;
import com.kh.clock.accommodation.repository.dto.AccomAdminListDTO;
import com.kh.clock.accommodation.repository.dto.AccomAdminSearchDTO;
import com.kh.clock.accommodation.repository.dto.AccomDTO;
import com.kh.clock.accommodation.repository.dto.AccomListInfoDTO;

public interface AccomService {
  
  // 숙박 목록 조회(키워드로 목록 조회)
  List<AccomDTO> selectAccomList(AccomListInfoDTO accomListInfoDTO);
  
  //숙박 상세 페이지 조회
  AccomDTO getAccommodationById(int accomSq);
  
  // 관리자 숙박 목록 조회
  List<AccomAdminListDTO> selectAdminAccomList(AccomAdminSearchDTO accomsSearchDTO);

  // 관리자 페이지 숙박 상세 조회
  AccomAdminDetailDTO selectAdminAccomDetailByAccomSq(int accomSq);

  // 관리자 페이지 숙박 상세 수정
  int updateAdminAccomDetail(AccomAdminDetailDTO updatedAccomInfo, MultipartFile[] images);

  // 관리자 페이지 숙박 정보 삭제
  int deleteAdminAccom(int accomSq);

  // 관리자 페이지 숙박 정보 등록
  int insertAdminAccom(AccomAdminDetailDTO accomDto, MultipartFile[] images);

}
