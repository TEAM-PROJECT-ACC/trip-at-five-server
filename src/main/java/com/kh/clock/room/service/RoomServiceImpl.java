package com.kh.clock.room.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.kh.clock.common.file.OclockFileUtils;
import com.kh.clock.common.file.UploadFileType;
import com.kh.clock.room.domain.RoomVO;
import com.kh.clock.room.repository.dao.RoomDAO;
import com.kh.clock.room.repository.dto.AvailableRoomRequestDTO;
import com.kh.clock.room.repository.dto.RoomCntDTO;
import com.kh.clock.room.repository.dto.RoomDetailDTO;
import com.kh.clock.room.repository.dto.RoomIdentifierDTO;
import com.kh.clock.room.repository.dto.RoomImageDTO;
import com.kh.clock.room.repository.dto.RoomListDTO;
import com.kh.clock.room.repository.dto.RoomSearchDTO;

@Service
public class RoomServiceImpl implements RoomService {
  
  private RoomDAO roomDAO;
  private RoomImageServiceImpl roomImageService;
  private OclockFileUtils oFileUtils; // 해당 클래스에서 @Component를 작성했으므로 의존성 주입 가능! 
  
  public RoomServiceImpl(RoomDAO roomDAO, OclockFileUtils oFileUtils, RoomImageServiceImpl roomImageService) {
    this.roomDAO = roomDAO;
    this.oFileUtils = oFileUtils;
    this.roomImageService = roomImageService;
  }

  /**
   * 전체 목록 조회
   */
  @Override
  public List<RoomListDTO> selectRoomList(RoomSearchDTO roomSearchDTO) {
    return roomDAO.selectRoomList(roomSearchDTO);
    }

  @Override
  public int selectTotalCount(int accomNo) {
    return roomDAO.selectTotalCount(accomNo);
  }
  
  /**
   * 객실 정보 저장
   */
  @Override
  @Transactional
  public int insertRoom(RoomVO room, MultipartFile[] images) {
//    System.out.println(room);
//    System.out.println(images);
    int insertResult = roomDAO.insertRoom(room);
//    System.out.println("insertResult : " + insertResult);
    
    insertImageFun(insertResult, room.getRoomSq(), images);
    
    return insertResult;
  }
  
  @Override
  @Transactional
  public int updateRoom(RoomVO room, MultipartFile[] images) {
    int updateResult = roomDAO.updateRoom(room); // 객실 정보 업데이트

    List<RoomImageDTO> roomImageList = roomImageService.findRoomImageByRoomSq(room.getRoomSq());
    
    additionalImageFun(updateResult, roomImageList, room.getRoomSq(), images);
  
    return updateResult;
  }
  
  /**
   * 이미지 등록 함수
   * @param judge : 실행 판단 값
   * @param typeNumKey : 유형번호값(숙박번호, 객실번호, 이용후기번호)
   * @param images : 새로 들어온 이미지 목록
   */
  private void insertImageFun(int judge, int typeNumKey, MultipartFile[] images) {
    int roomImageResult = 0;
    List<MultipartFile> newImageList = new ArrayList<>();
    List<String> deleteList = new ArrayList<>();
    if(judge > 0) {
      
      // 파일이 있을 경우만 실행
      if(images != null) {
        String typePath = UploadFileType.ROOM.getPath();
        
        // hash 코드 구하기
        List<String> hashCodeList = oFileUtils.getHashCodeList(images, typePath);

        for(int i = 0; i < images.length; i++) {
//          System.out.println("구한 hash값 : " + hashCodeList.get(i));
//          System.out.println("images[i] : " + images[i]);
          newImageList.add(images[i]);
          deleteList.add(hashCodeList.get(i));
        }
        
        // 객실 이미지 처리
        List<String> fileUrls = oFileUtils.saveImage(newImageList, typePath);    
        for(int i = 0; i < fileUrls.size(); i++) {
          roomImageResult += roomImageService.insertRoomImage(new RoomImageDTO(hashCodeList.get(i), newImageList.get(i).getOriginalFilename(), fileUrls.get(i), typeNumKey));
        }
        // 전달 받은 파일의 갯수와 DB에서 INSERT 한 행의 갯수가 동일하면 저장 성공!
        if(roomImageResult == fileUrls.size()) {
//          System.out.println("파일 데이터 저장 성공!");
          
          // 임시 폴더 내 파일 삭제
          oFileUtils.deleteTempFolder(newImageList, deleteList, hashCodeList, typePath);
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
  private void additionalImageFun(int judge, List<RoomImageDTO> roomImageList, int typeNumKey, MultipartFile[] images) {
    int roomImageResult = 0;
    List<MultipartFile> newImageList = new ArrayList<>();
    
    if(judge > 0) {
      
      // 파일이 있을 경우만 실행
      if(images != null) {
        String typePath = UploadFileType.ROOM.getPath();

        // 이미지 처리 전 이미지의 해시값으로 중복 여부를 판단해서 중복 없는 이미지만 등록
        // hash 코드를 비교해서 다를 경우 새로운 파일로 인식하여 새로 저장할 배열에 추가

        // 새로 요청받은 이미지 배열의 해시값 리스트
        List<String> hashCodeList = oFileUtils.getHashCodeList(images, typePath);
        List<String> newHashCodeList = new ArrayList<>();
        
        // 해시값 비교
        for(int i = 0; i < hashCodeList.size(); i++) {
          int count = 0;
//          System.out.println(hashCodeList.get(i));
//          System.out.println(roomImageList.get(i).getRoomImgHashCd());
          for(int j = 0; j < roomImageList.size(); j++) {
            if(hashCodeList.get(i).equals(roomImageList.get(j).getRoomImgHashCd())) {
              System.out.println("값이 일치");
              count++;
            } 
          }

          
          if(count == 0) {
            newHashCodeList.add(hashCodeList.get(i));
            newImageList.add(images[i]); 
          }
        }
        
        // 객실 이미지 처리
        if(newImageList.size() > 0) {
          List<String> fileUrls = oFileUtils.saveImage(newImageList, UploadFileType.ROOM.getPath());    
          for(int i = 0; i < fileUrls.size(); i++) {
            roomImageResult += roomImageService.insertRoomImage(new RoomImageDTO(newHashCodeList.get(i), newImageList.get(i).getOriginalFilename(), fileUrls.get(i), typeNumKey));
          }
          
          if(roomImageResult == fileUrls.size()) {
            System.out.println("파일 데이터 저장 성공!");
          }
        } else {
       // 임시 폴더 내 파일 삭제
          for(int i = 0; i < images.length; i++) {
            newImageList.add(images[i]);
          }
        }

        oFileUtils.deleteTempFolder(newImageList, newHashCodeList, hashCodeList, typePath);
      }
    }
  }

  @Override
  @Transactional
  public int deleteRoomAndRoomImageByAccomNoAndRoomSq(RoomIdentifierDTO roomIdenDTO) {
    /**
     * 1. 객실번호로 해당 객실 이미지 조회
     * 
     * 2. 객실 이미지 path 값으로 해당 경로에 파일이 있는 지 체크
     * 
     * 2-1. 체크 이후 파일 삭제
     * 
     * 3. 객실번호로 DB에서 데이터 삭제
     */
    List<RoomImageDTO> roomImagePathList = roomImageService.findRoomImageByRoomSq(roomIdenDTO.getRoomSq());
    
//    for(RoomImageDTO r : roomImagePathList) System.out.println("삭제를 위해 조회한 이미지 : " + r.getRoomImgPathName());
    
    boolean flag = false;
    for(int i = 0; i < roomImagePathList.size(); i++) {
      flag = oFileUtils.deleteFile(roomImagePathList.get(i).getRoomImgPathName()); // 파일 삭제 함수 호출
//      System.out.println("flag: " + flag);
      if(!flag) {
        System.out.println(
            roomImagePathList.get(i).getRoomImgPathName()
            + " 경로에 있는 " + roomImagePathList.get(i).getRoomImgOrgName()
            + " 파일을 삭제하지 못했습니다.");
        break;
      }
    }

    int deleteResult = roomDAO.deleteRoomAndRoomImageByAccomNoAndRoomSq(roomIdenDTO);
    
    return deleteResult;
  }
  
  @Override
  public RoomDetailDTO findRoomByAccomNoAndRoomSq(RoomIdentifierDTO getRoomDTO) {
    return roomDAO.findRoomByAccomNoAndRoomSq(getRoomDTO);
  }

  /**
   * 숙박번호로 객실과 객실 수 조회
   * @param availableRoomRequestDTO
   * @return
   */
  @Override
  public List<RoomCntDTO> selectRoomCnt(AvailableRoomRequestDTO availableRoomRequestDTO) {
    return roomDAO.selectRoomCnt(availableRoomRequestDTO);
  }
}
