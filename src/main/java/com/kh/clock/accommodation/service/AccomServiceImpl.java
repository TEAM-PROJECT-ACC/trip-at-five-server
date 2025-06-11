package com.kh.clock.accommodation.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.kh.clock.accommodation.repository.dao.AccomDAO;
import com.kh.clock.accommodation.repository.dto.AccomAdminDetailDTO;
import com.kh.clock.accommodation.repository.dto.AccomAdminImageDTO;
import com.kh.clock.accommodation.repository.dto.AccomAdminListDTO;
import com.kh.clock.accommodation.repository.dto.AccomAdminSearchDTO;
import com.kh.clock.accommodation.repository.dto.AccomDTO;
import com.kh.clock.accommodation.repository.dto.AccomListInfoDTO;
import com.kh.clock.common.file.OclockFileUtils;
import com.kh.clock.common.file.UploadFileType;
import com.kh.clock.room.domain.RoomVO;
import com.kh.clock.room.repository.dto.RoomImageDTO;

@Service
public class AccomServiceImpl implements AccomService {
  
  private final AccomDAO accomDAO;
  private OclockFileUtils oFileUtils;
  private AccomImageServiceImpl accomImageService;
  
  public AccomServiceImpl(AccomDAO accomDAO, OclockFileUtils oFileUtils, AccomImageServiceImpl accomImageService) {
      this.accomDAO = accomDAO;
      this.oFileUtils = oFileUtils;
      this.accomImageService = accomImageService;
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
  @Transactional
  public int updateAdminAccomDetail(AccomAdminDetailDTO updatedAccomInfo, MultipartFile[] images) {
    int updateResult = accomDAO.updateAdminAccomDetail(updatedAccomInfo);

    List<AccomAdminImageDTO> accomImageList = accomImageService.findAccomImageByAccomSq(updatedAccomInfo.getAccomSq());
    
    additionalImageFun(updateResult, accomImageList, updatedAccomInfo.getAccomSq(), images);
  
    return updateResult;
  }

  // 관리자 숙박 삭제
  @Override
  public int deleteAdminAccom(int accomSq) {
    return accomDAO.deleteAdminAccom(accomSq);
  }

  //관리자 숙박 정보 등록
  @Override
  @Transactional
  public int insertAdminAccom(AccomAdminDetailDTO accomDto, MultipartFile[] images) {
//    System.out.println("accomDto : " + accomDto);
    int insertResult = accomDAO.insertAdminAccom(accomDto);
//    System.out.println("accomDto.getAccomSq() : " + accomDto.getAccomSq());
    insertImageFun(insertResult, accomDto.getAccomSq(), images);
    
    return insertResult;
  }
  
  /**
   * 이미지 등록 함수
   * @param judge : 실행 판단 값
   * @param typeNumKey : 유형번호값(숙박번호, 객실번호, 이용후기번호)
   * @param images : 새로 들어온 이미지 목록
   */
  private void insertImageFun(int judge, int typeNumKey, MultipartFile[] images) {
    int accomImageResult = 0;
    List<MultipartFile> newImageList = new ArrayList<>();
    List<String> newHashCodeList = new ArrayList<>();
    if(judge > 0) {
      
      if(images != null) {
        String typePath = UploadFileType.ACC.getPath();
        
        List<String> hashCodeList = oFileUtils.getHashCodeList(images, typePath);

        for(int i = 0; i < images.length; i++) {
//          System.out.println("구한 hash값 : " + hashCodeList.get(i));
//          System.out.println("images[i] : " + images[i]);
          newImageList.add(images[i]);
          newHashCodeList.add(hashCodeList.get(i));
        }
        
        List<String> fileUrls = oFileUtils.saveImage(newImageList, typePath);    
        for(int i = 0; i < fileUrls.size(); i++) {
          accomImageResult += accomImageService.insertAccomImage(new AccomAdminImageDTO(hashCodeList.get(i), newImageList.get(i).getOriginalFilename(), fileUrls.get(i), typeNumKey));
        }

        if(accomImageResult == fileUrls.size()) {
          System.out.println("파일 데이터 저장 성공!");
          
          oFileUtils.deleteTempFolder(newImageList, newHashCodeList, hashCodeList, typePath);
        }
      }
    }
  }
  
  /**
   * 추가 이미지 hashCode 값 비교 후 새로운 이미지만 INSERT 처리 메서드
   * @param judge
   * @param roomImageList : 유형번호값으로 조회한 이미지 목록
   * @param typeNumKey : 유형번호값(숙박번호, 객실번호, 이용후기번호)
   * @param images : 새로 요청받은 이미지 배열
   */
  private void additionalImageFun(int judge, List<AccomAdminImageDTO> accomImageList, int typeNumKey, MultipartFile[] images) {
    int roomImageResult = 0;
    List<MultipartFile> newImageList = new ArrayList<>();
    
    if(judge > 0) {
      
      // 파일이 있을 경우만 실행
      if(images != null) {
        String typePath = UploadFileType.ACC.getPath();

        List<String> hashCodeList = oFileUtils.getHashCodeList(images, typePath);
        List<String> newHashCodeList = new ArrayList<>();
        
        // 해시값 비교
        for(int i = 0; i < hashCodeList.size(); i++) {
          int count = 0;
          for(int j = 0; j < accomImageList.size(); j++) {
            if(hashCodeList.get(i).equals(accomImageList.get(j).getAccomImgHashCd())) {
//              System.out.println("값이 일치");
              count++;
            } 
          }
          
          if(count == 0) {
            newHashCodeList.add(hashCodeList.get(i));
            newImageList.add(images[i]); 
          }
        }
        
        if(newImageList.size() > 0) {
          List<String> fileUrls = oFileUtils.saveImage(newImageList, UploadFileType.ACC.getPath());    
          for(int i = 0; i < fileUrls.size(); i++) {
            roomImageResult += accomImageService.insertAccomImage(new AccomAdminImageDTO(newHashCodeList.get(i), newImageList.get(i).getOriginalFilename(), fileUrls.get(i), typeNumKey));
          }
          
          if(roomImageResult == fileUrls.size()) {
            System.out.println("파일 데이터 저장 성공!");
          }
        } else {
          for(int i = 0; i < images.length; i++) {
            newImageList.add(images[i]);
          }
        }

        oFileUtils.deleteTempFolder(newImageList, newHashCodeList, hashCodeList, typePath);
      }
    }
  }
}
