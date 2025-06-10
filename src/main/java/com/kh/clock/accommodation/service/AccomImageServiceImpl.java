package com.kh.clock.accommodation.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.kh.clock.accommodation.repository.dao.AccomImageDAO;
import com.kh.clock.accommodation.repository.dto.AccomAdminImageDTO;
import com.kh.clock.common.file.OclockFileUtils;
import com.kh.clock.common.file.dto.ImageFileDTO;

@Service
public class AccomImageServiceImpl implements AccomImageService{
  private AccomImageDAO accomImageDAO;
  private OclockFileUtils oFileUtils;
  
  public AccomImageServiceImpl(AccomImageDAO accomImageDAO, OclockFileUtils oFileUtils) {
    this.accomImageDAO = accomImageDAO;
    this.oFileUtils = oFileUtils;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public int insertAccomImage(AccomAdminImageDTO accomImageDTO) {
    return accomImageDAO.insertAccomImage(accomImageDTO);
  }

  /**
   * 객실번호로 객실 이미지 조회
   */
  @Override
  public List<AccomAdminImageDTO> findAccomImageByAccomSq(int accomSq) {
    return accomImageDAO.findAccomImageByAccomSq(accomSq);
  }

  /**
   * 객실 이미지 수정
   */
  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public int updateAccomImage(AccomAdminImageDTO accomImageDTO) {
    return accomImageDAO.updateAccomImage(accomImageDTO);
  }

  /**
   * 객실 이미지 삭제
   * @param imageList 
   */
  @Override
  public int deleteAccomImageByAccomSq(List<ImageFileDTO> imageList) {
    int result = 0;
    for(int i = 0; i < imageList.size(); i++) {
      oFileUtils.deleteFile(imageList.get(i).getImagePath());
      
      result += accomImageDAO.deleteAccomImageByAccomSq(imageList.get(i));
    }

    return result;
  }
}
