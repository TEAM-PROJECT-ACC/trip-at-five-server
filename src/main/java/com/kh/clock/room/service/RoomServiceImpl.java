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
public class RoomServiceImpl implements RoomSerivce {
  
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
    int roomImageResult = 0;
//    System.out.println(room);
//    System.out.println(images);
    int insertResult = roomDAO.insertRoom(room);
//    System.out.println("insertResult : " + insertResult);
    
    if(insertResult > 0) {
      /**
       * TODO: 객실은 저장이 됨. 하지만 가장 최근에 저장한 객실ID값으로 객실이미지를 저장해야하는 것을 해야함
       * => 트랜잭션 처리로 해결 완료
       */
      int roomNo = room.getRoomSq(); // 트랜잭션 처리로 인해 방금 INSERT 한 객실 번호 값 불러오기
      
      // 파일이 있을 경우만 실행
      if(images != null) {
        // 객실 이미지 처리
        List<String> fileUrls = oFileUtils.saveRoomImage(images, UploadFileType.ROOM.getPath());
        for(int i = 0; i < fileUrls.size(); i++) {
          roomImageResult += roomImageService.insertRoomImage(new RoomImageDTO(images[i].getOriginalFilename(), fileUrls.get(i), roomNo));
        }
        
        // 전달 받은 파일의 갯수와 DB에서 INSERT 한 행의 갯수가 동일하면 저장 성공!
        if(roomImageResult == fileUrls.size()) {
          System.out.println("파일 데이터 저장 성공!");
        }
      }
    }
    
    return insertResult;
  }

  @Override
  @Transactional
  public int updateRoom(RoomVO room, MultipartFile[] images) {
    int roomImageResult = 0;
//  System.out.println(room);
//  System.out.println(images);
    int updateResult = roomDAO.updateRoom(room);
//  System.out.println(updateResult);
    
    /**
     * 객실 이미지 업데이트 처리 로직
     * 
     * 1. 객실번호 값으로 객실 이미지 데이터 목록을 조회
     * 
     * 2. 조회된 데이터 목록의 이미지 원본명과 images 의 이미지 원본명을 조회된 데이터 수만큼 반복하고
     *      그 안에서 images 크기만큼 반복하여 비교한다.
     * 
     * 2-1. 원본명이 서로 동일 => continue
     *      원본명이 불일치 => 삭제할 
     * 
     * 2-2. 
     */
  
    if(updateResult > 0) {
      int roomNo = room.getRoomSq();
      
      // 파일이 있을 경우만 실행
      if(images != null) {
        // 객실 이미지 처리
        List<String> fileUrls = oFileUtils.saveRoomImage(images, UploadFileType.ROOM.getPath());
        for(int i = 0; i < fileUrls.size(); i++) {
          roomImageResult += roomImageService.updateRoomImage(new RoomImageDTO(images[i].getOriginalFilename(), fileUrls.get(i), roomNo));
        }
        
        if(roomImageResult == fileUrls.size()) {
          System.out.println("파일 데이터 저장 성공!");
        }
      }
    }
  
    return updateResult;
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
