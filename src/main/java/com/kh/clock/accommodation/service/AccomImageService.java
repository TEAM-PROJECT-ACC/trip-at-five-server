package com.kh.clock.accommodation.service;

import java.util.List;
import com.kh.clock.accommodation.repository.dto.AccomAdminImageDTO;
import com.kh.clock.common.file.dto.ImageFileDTO;

public interface AccomImageService {

  /**
   * 객실 이미지 저장
   * @param accomImageDTO
   * @return
   */
  int insertAccomImage(AccomAdminImageDTO accomImageDTO);

  /**
   * 객실번호로 객실 이미지 조회
   */
  List<AccomAdminImageDTO> findAccomImageByAccomSq(int accomSq);

  /**
   * 객실 이미지 삭제
   * @param imageList 
   */
  int deleteAccomImageByAccomSq(List<ImageFileDTO> imageList);
}
