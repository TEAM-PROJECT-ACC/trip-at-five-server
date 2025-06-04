package com.kh.clock.room.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.kh.clock.common.file.OclockFileUtils;
import com.kh.clock.common.file.UploadFileType;
import com.kh.clock.room.domain.RoomVO;
import com.kh.clock.room.repository.dao.RoomDAO;
import com.kh.clock.room.repository.dto.RoomDetailDTO;
import com.kh.clock.room.repository.dto.RoomIdentifierDTO;
import com.kh.clock.room.repository.dto.RoomImageDTO;
import com.kh.clock.room.repository.dto.RoomListDTO;

@Service
public class RoomServiceImpl implements RoomService {
  
  @Value("${file.dir}")
  private String staticFilePath;
  
  @Value("${file.delete}")
  private String deletePath;
  
  
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
  public List<RoomListDTO> selectAllList(int accomNo) {
    List<RoomListDTO> roomList = roomDAO.selectAllList(accomNo);
    
    return roomList != null ? roomList : null;
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
    
//    if(insertResult > 0) {
      /**
       * TODO: 객실은 저장이 됨. 하지만 가장 최근에 저장한 객실ID값으로 객실이미지를 저장해야하는 것을 해야함
       * => 트랜잭션 처리로 해결 완료
       */
//      int roomNo = room.getRoomSq(); // 트랜잭션 처리로 인해 방금 INSERT 한 객실 번호 값 불러오기
      
//      // 파일이 있을 경우만 실행
//      if(images != null) {
//        // 객실 이미지 처리
//        List<String> fileUrls = oFileUtils.saveRoomImage(images, UploadFileType.ROOM.getPath());
//        for(int i = 0; i < fileUrls.size(); i++) {
//          roomImageResult += roomImageService.insertRoomImage(new RoomImageDTO(images[i].getOriginalFilename(), fileUrls.get(i), roomNo));
//        }
//        
//        // 전달 받은 파일의 갯수와 DB에서 INSERT 한 행의 갯수가 동일하면 저장 성공!
//        if(roomImageResult == fileUrls.size()) {
//          System.out.println("파일 데이터 저장 성공!");
//        }
//      }
//    }
    
    return insertResult;
  }
  
  private void insertImageFun(int judge, int typeNumKey, MultipartFile[] images) {
    int roomImageResult = 0;
    List<MultipartFile> newImageList = new ArrayList<>();
    if(judge > 0) {
      
      // 파일이 있을 경우만 실행
      if(images != null) {
        // hash 코드 구하기
        List<String> hashCodeList = oFileUtils.getHashCodeList(images);

        for(int i = 0; i < images.length; i++) {
          System.out.println("구한 hash값 : " + hashCodeList.get(i));
          System.out.println("images[i] : " + images[i]);
          newImageList.add(images[i]);
        }
        
        // 객실 이미지 처리
        List<String> fileUrls = oFileUtils.saveRoomImage(newImageList, UploadFileType.ROOM.getPath());    
        for(int i = 0; i < fileUrls.size(); i++) {
          roomImageResult += roomImageService.insertRoomImage(new RoomImageDTO(hashCodeList.get(i), newImageList.get(i).getOriginalFilename(), fileUrls.get(i), typeNumKey));
        }
        
        if(roomImageResult == fileUrls.size()) {
          System.out.println("파일 데이터 저장 성공!");
          oFileUtils.deleteTempFolder(newImageList);
        }
      }
    }
  }

  @Override
  @Transactional
  public int updateRoom(RoomVO room, MultipartFile[] images) {
    int updateResult = roomDAO.updateRoom(room);

    List<RoomImageDTO> roomImageList = roomImageService.findRoomImageByRoomSq(room.getRoomSq());
    
//    for(RoomImageDTO r : roomImagePathList) System.out.println("삭제를 위해 조회한 이미지 : " + r.getRoomImgPathName());
    
    additionalImageFun(updateResult, roomImageList, room.getRoomSq(), images);
  
    return updateResult;
  }
  
  /**
   * 추가 이미지 hashCode 값 비교 후 새로운 이미지만 INSERT 처리 메서드
   * @param judge
   * @param roomImageList : 해당 번호값으로 조회한 이미지 목록
   * @param typeNumKey : 숙박인지 객실인지
   * @param images : 새로 요청받은 이미지 배열
   */
  private void additionalImageFun(int judge, List<RoomImageDTO> roomImageList, int typeNumKey, MultipartFile[] images) {
    int roomImageResult = 0;
    List<MultipartFile> newImageList = new ArrayList<>();
    List<String> newHashCodeList = oFileUtils.getHashCodeList(images);
    if(judge > 0) {
      
      // 파일이 있을 경우만 실행
      if(images != null) {

        // 이미지 처리 전 이미지의 해시값으로 중복 여부를 판단해서 중복 없는 이미지만 등록
        
        for(int i = 0; i < images.length; i++) {
          for(int j = 0; j < roomImageList.size(); j++) {
            
            // hash 코드를 비교해서 다를 경우 새로운 파일로 인식하여 새로 저장할 배열에 추가
            if(!newHashCodeList.get(i).equals(roomImageList.get(j).getRoomImgHashCd())) {
              newImageList.add(images[i]);
            } else newHashCodeList.remove(i);
          }
        }
        
        // 객실 이미지 처리
        List<String> fileUrls = oFileUtils.saveRoomImage(newImageList, UploadFileType.ROOM.getPath());    
        for(int i = 0; i < fileUrls.size(); i++) {
          roomImageResult += roomImageService.insertRoomImage(new RoomImageDTO(newImageList.get(i).getOriginalFilename(), fileUrls.get(i), newHashCodeList.get(i), typeNumKey));
        }
        
        if(roomImageResult == fileUrls.size()) {
          System.out.println("파일 데이터 저장 성공!");
        }
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
    
    for(RoomImageDTO r : roomImagePathList) System.out.println("삭제를 위해 조회한 이미지 : " + r.getRoomImgPathName());
    
    boolean flag = false;
    for(int i = 0; i < roomImagePathList.size(); i++) {
      flag = deleteFile(roomImagePathList.get(i).getRoomImgPathName()); // 파일 삭제 함수 호출
//      System.out.println("flag: " + flag);
      if(!flag) {
        System.out.println(
            roomImagePathList.get(i).getRoomImgPathName()
            + " 경로에 있는 " + roomImagePathList.get(i).getRoomImgOrgName()
            + " 파일을 삭제하지 못했습니다.");
        break;
      }
    }
    
//    if(flag) roomImageService.deleteRoomImageByRoomSq(roomIdenDTO.getRoomSq());
    
    
    int deleteResult = roomDAO.deleteRoomAndRoomImageByAccomNoAndRoomSq(roomIdenDTO);
    
    
    return deleteResult;
  }
  
  // 파일 삭제
  private boolean deleteFile(String path) {
     String basePath = new File(deletePath).getAbsolutePath(); // 절대경로
     String fullPath = basePath + File.separator + path;
  
     File file = new File(fullPath);
     System.out.println("삭제 경로: " + file.getPath());
  
     if (file.exists()) {
         if (file.delete()) {
             System.out.println("파일 삭제 성공");
             return true;
         } else {
             System.out.println("파일 삭제 실패");
         }
     } else {
         System.out.println("파일이 존재하지 않음");
     }
  
     return false;
  }



  @Override
  public RoomDetailDTO findRoomByAccomNoAndRoomSq(RoomIdentifierDTO getRoomDTO) {
    return roomDAO.findRoomByAccomNoAndRoomSq(getRoomDTO);
  }
}
